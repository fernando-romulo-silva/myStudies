package br.com.fernando.server.application;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.fernando.core.Usuario;

// Atualmente, os serviços REST estão padronizados sob uma única especificação, em Java, chamada JAX-RS. 
// Esta especificação funciona no mesmo molde que outras especificações, ou seja, provê um conjunto 
// de anotações/classes e deixa a cargo de vários frameworks o funcionamento propriamente dito.
// 
// @Path – essa anotação recebe uma string como parâmentro e indica qual é o path da URL. No exemplo anterior, 
// tivemos a classe anotada com o valor ”/helloworld”, e por isso que acessamos a URL;
//
// @GET – anotação que indica qual o método correspondente do HTTP. Como dito anteriormente, podemos ter a mesma 
// URL para ações diferentes desde que o método HTTP também seja diferente. Da mesma forma, temos as anotações @POST, @PUT e @DELETE.
//
// @Produces – anotação que indica qual o mime-type do conteúdo da resposta que será enviada para o cliente. 
// No exemplo acima, foi “text/plain” para indicar que é texto puro. Em um web service isso é pouco usual, em geral vamos utilizar valores como 
// “text/xml” para devolver XML.
//
// @Consumes – anotação que indica qual o mime-type do conteúdo da requisição. Em geral é utilizado principalmente em requisições 
// do tipo POST ou PUT, em que o cliente precisa enviar a informação do que ele deseja adicionar/alterar. 
// Do mesmo jeito que o web service “devolve” XML, ele pode “consumir” (receber) conteúdo XML.
//
// @PathParam - serve para indicar que o parâmetro do método se refere ao parâmetro no path.
//
// @Context - Identifica um alvo a ser injetado pelo contêiner, como servlets....

@Path("/usuarios")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public interface UsuariosServiceInterface {

    public static final String CAMPO_DESCRICAO_IMAGEM = "Descricao";

    static final String PARAM_INICIO = "inicio";

    static final String PARAM_TAMANHO_PAGINA = "tamanhoPagina";

    // Para enviar/receber dados no Header da mensagem
    //
    // Header com 'If-Modified-Since' é utilizado para enviar uma data para o servidor, dizendo a este que o
    // cliente só deseja o resultado da requisição se o recurso tiver sido modificado desde então.
    @GET
    public Response listarUsuarios(@HeaderParam("If-Modified-Since") Date modifiedSince, @QueryParam(PARAM_INICIO) @DefaultValue("0") Integer inicio, @QueryParam(PARAM_TAMANHO_PAGINA) @DefaultValue("20") Integer tamanhoPagina,
            @Context UriInfo uriInfo);

    @GET
    @Path("/{id}")
    public Response find(@PathParam("id") Long id, @HeaderParam("If-Modified-Since") Date modifiedSince);

    // Esta interface é gerenciada pela própria engine do JAX-RS, e não precisamos instanciá-la;
    // basta apenas injetá-la no nosso serviço, utilizando a anotação javax.ws.rs.core.Context .
    // Podemos utilizar esse mecanismo para injetar uma instância de UriInfo na própria classe de serviço,
    // como um atributo, ou como parâmetro de um método. Dentre os vários métodos utilitários fornecidos
    // pela interface UriInfo , está 'getAbsolutePathBuilder'. Este método traz a URL que foi invocada
    // (exceto query strings) para chegar ao método atual em um builder; ou seja, é possível adicio-
    // nar dados à URI invocada. Desta forma, se realizarmos uma requisição POST para

    @POST
    public Response create(@Context UriInfo uriInfo, Usuario usuario);

    @PUT
    public Response update(Usuario usuario);

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Usuario usuario);

    @DELETE
    public Response delete(Usuario usuario);

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id);

    @PUT
    @Path("/{id}")
    @Consumes("image/*")
    public Response adicionarImagem(@HeaderParam(CAMPO_DESCRICAO_IMAGEM) String descricao, @PathParam("id") Long idUsuario, @Context HttpServletRequest httpServletRequest, byte[] dadosImagem);

    @GET
    @Path("/{id}")
    @Produces("image/*")
    // defini que a resposta do servico pode ser todo tipo de imagem
    public Response recuperarImagem(@PathParam("id") Long id, @HeaderParam("If-Modified-Since") Date modifiedSince);

}