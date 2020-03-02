package br.com.fernando.server.application.security;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.ws.security.WSPasswordCallback;

import br.com.fernando.core.Base64;
import br.com.fernando.core.DateUtil;

public class CallbackSeguranca implements CallbackHandler {

    private static String USUARIO = "admin";

    private static String SENHA = "admin";

    private static ChaveRSA chaveRSA;

    static {
        try {
            chaveRSA = ChaveRSA.carregar();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String ENDERECO_SERVICO_USUARIOS = "https://localhost:8181/soa-aplicado/services";

    private static Map<String, Usuario> cache = new ConcurrentHashMap<>();

    @Override
    public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                final WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
                final Usuario usuario = encontreUsuario(pc.getIdentifier());
                if (usuario == null) {
                    return;
                }
                pc.setPassword(usuario.getSenhaDecodificada());
            }
        }
    }

    private Usuario encontreUsuario(final String login) throws IOException {
        Usuario usuario = null;
        try {

            final Client client = ClientBuilder.newClient();

            final WebTarget usuariosTarget = client.target(ENDERECO_SERVICO_USUARIOS + "/usuarios").path("/{login}").resolveTemplate("login", login);

            final Entity<ChaveRSA> entity = Entity.entity(chaveRSA, MediaType.APPLICATION_XML);

            final Builder request = usuariosTarget.request(MediaType.APPLICATION_XML);

            if (cache.containsKey(login)) {
                usuario = cache.get(login);
                request.header("If-Modified-Since", DateUtil.formatDate(usuario.getDataAtualizacao()));
            }

            final Invocation usuariosInvocation = request.header("Authorization", getAuth()).buildPost(entity);

            final Response response = usuariosInvocation.invoke();

            if (response.getStatus() == Status.NOT_MODIFIED.getStatusCode()) {
                return usuario;
            }

            if (response.getStatus() == Status.OK.getStatusCode()) {
                usuario = response.readEntity(Usuario.class);

                final Date date = DateUtil.parseDate((String) response.getHeaders().getFirst("Date"));
                usuario.setDataAtualizacao(date);
                cache.put(login, usuario);
                return usuario;
            }

            if (response.getStatus() == Status.NOT_FOUND.getStatusCode()) {
                return null;
            }

            throw new Exception("Usuário não localizado");
        } catch (final Exception e) {
            throw new IOException("Não foi possível recuperar as informações do usuário");
        }

    }

    private String getAuth() {
        return "Basic " + Base64.encodeBytes((USUARIO + ":" + SENHA).getBytes());
    }

}
