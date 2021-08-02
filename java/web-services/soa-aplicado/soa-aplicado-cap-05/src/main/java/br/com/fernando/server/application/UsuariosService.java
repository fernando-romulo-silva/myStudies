package br.com.fernando.server.application;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.fernando.core.Imagem;
import br.com.fernando.core.Usuario;
import br.com.fernando.core.Usuarios;
import br.com.fernando.core.rest.Link;

@Stateless
public class UsuariosService implements UsuariosServiceInterface {

    @PersistenceContext(name = "soa-aplicadoPU")
    private EntityManager em;

    @Override
    public Response listarUsuarios(final Date modifiedSince, final Integer inicio, final Integer tamanhoPagina, final UriInfo uriInfo) {

        final Collection<Usuario> usuarios = em.createQuery("select u from Usuario u", Usuario.class).setFirstResult(inicio).setMaxResults(tamanhoPagina.intValue()).getResultList();

        // Recuperamos o número de usuários presentes em nossa base
        // para que possamos realizar o cálculo de páginas
        final Long numeroUsuarios = em.createQuery("select count(u) from Usuario u", Long.class).getSingleResult();

        boolean atualizado = false;

        if (modifiedSince != null) {
            for (final Usuario usuario : usuarios) {
                if (usuario.getDataAtualizacao().after(modifiedSince)) {
                    atualizado = true;
                    break;
                }
            }
        } else {
            // Se a data não tiver sido passada, deve considerar os recursos
            // como 'mais atuais'
            atualizado = true;
        }

        if (atualizado) {

            for (final Usuario usuario : usuarios) {
                final Link link = criarLinkImagemUsuario(usuario);
                usuario.adicionarLink(link);
            }

            return Response.ok(new Usuarios(usuarios, criarLinksUsuarios(uriInfo, tamanhoPagina, inicio, numeroUsuarios))).build();
        } else {
            return Response.notModified().build();
        }

    }

    @Override
    public Response find(final Long id, final Date modifiedSince) {
        final Usuario usuario = em.find(Usuario.class, id);

        if (usuario != null) {
            if (modifiedSince == null || modifiedSince != null && usuario.getDataAtualizacao().after(modifiedSince)) {
                usuario.adicionarLink(criarLinkImagemUsuario(usuario));
                return Response.ok(usuario).build();
            }

            return Response.notModified().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @Override
    public Response create(final UriInfo uriInfo, final Usuario usuario) {
        em.persist(usuario);

        // Constrói a URL onde o recurso estará disponível

        final UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        final URI location = uriBuilder.path("/{id}").build(usuario.getId());

        return Response.created(location).build();
    }

    @Override
    public Response update(Usuario usuario) {
        usuario = em.merge(usuario);
        return Response.noContent().build();
    }

    @Override
    public Response update(final Long id, final Usuario usuario) {
        usuario.setId(id);
        return update(usuario);
    }

    @Override
    public Response delete(Usuario usuario) {
        usuario = em.find(Usuario.class, usuario.getId());
        if (usuario == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(usuario);
        return Response.noContent().build();
    }

    @Override
    public Response delete(final Long id) {
        final Usuario usuario = new Usuario();
        usuario.setId(id);
        return delete(usuario);
    }

    @Override
    public Response adicionarImagem(final String descricao, final Long idUsuario, final HttpServletRequest httpServletRequest, final byte[] dadosImagem) {
        final Usuario usuario = em.find(Usuario.class, idUsuario);
        if (usuario == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        final Imagem imagem = new Imagem();
        imagem.setDados(dadosImagem);
        imagem.setDescricao(descricao);
        imagem.setTipo(httpServletRequest.getContentType());
        usuario.setImagem(imagem);
        em.merge(usuario);
        return Response.noContent().build();
    }

    @Override
    public Response recuperarImagem(final Long id, final Date modifiedSince) {
        final Usuario usuario = em.find(Usuario.class, id);
        if (usuario == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        final Imagem imagem = usuario.getImagem();

        if (modifiedSince != null && imagem.getDataAtualizacao().before(modifiedSince)) {
            return Response.notModified().build();
        }

        return Response.ok(imagem.getDados(), imagem.getTipo()).header(CAMPO_DESCRICAO_IMAGEM, imagem.getDescricao()).build();
    }

    private Link criarLinkImagemUsuario(final Usuario usuario) {
        final String uri = UriBuilder.fromPath("usuarios/{id}").build(usuario.getId()).toString();
        final String rel = "imagem";
        final String type = "image/*";

        return new Link(uri, rel, type);
    }

    private Link[] criarLinksUsuarios(final UriInfo uriInfo, final Integer tamanhoPagina, final Integer inicio, final Long numeroUsuarios) {
        final Collection<Link> links = new ArrayList<>();

        final double numeroUsuariosDouble = numeroUsuarios;
        final double tamanhoPaginaDouble = tamanhoPagina;

        // Arrendondamento para cima, para fornecer o número certo de
        // páginas
        final Long numeroPaginas = (long) Math.ceil(numeroUsuariosDouble / tamanhoPaginaDouble);

        // O resultado da divisão será um int.
        final Long paginaAtual = new Long(inicio / tamanhoPagina);

        final Link linkPrimeiraPagina = new Link(UriBuilder.fromPath(uriInfo.getPath()).queryParam(PARAM_INICIO, 0).queryParam(PARAM_TAMANHO_PAGINA, tamanhoPagina).build().toString(), "primeiraPagina");
        links.add(linkPrimeiraPagina);

        if (paginaAtual > 0) {
            if (paginaAtual <= numeroPaginas) {
                final Link linkPaginaAnterior = new Link(UriBuilder.fromPath(uriInfo.getPath()).queryParam(PARAM_INICIO, (paginaAtual - 1) * tamanhoPagina).queryParam(PARAM_TAMANHO_PAGINA, tamanhoPagina).build().toString(), "paginaAnterior");
                links.add(linkPaginaAnterior);
            } else {
                final Link linkPaginaAnterior = new Link(UriBuilder.fromPath(uriInfo.getPath()).queryParam(PARAM_INICIO, (numeroPaginas - 1) * tamanhoPagina).queryParam(PARAM_TAMANHO_PAGINA, tamanhoPagina).build().toString(), "paginaAnterior");
                links.add(linkPaginaAnterior);
            }
        }

        if (paginaAtual < numeroPaginas - 1) {
            final Link linkProximaPagina = new Link(UriBuilder.fromPath(uriInfo.getPath()).queryParam(PARAM_INICIO, (paginaAtual + 1) * tamanhoPagina).queryParam(PARAM_TAMANHO_PAGINA, tamanhoPagina).build().toString(), "proximaPagina");
            links.add(linkProximaPagina);
        }

        final Link linkUltimaPagina = new Link(UriBuilder.fromPath(uriInfo.getPath()).queryParam(PARAM_INICIO, (numeroPaginas - 1) * tamanhoPagina).queryParam(PARAM_TAMANHO_PAGINA, tamanhoPagina).build().toString(), "ultimaPagina");
        links.add(linkUltimaPagina);

        return links.toArray(new Link[] {});
    }
}
