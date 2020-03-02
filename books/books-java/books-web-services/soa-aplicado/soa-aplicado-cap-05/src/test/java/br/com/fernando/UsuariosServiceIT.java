package br.com.fernando;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.fernando.core.Usuario;
import br.com.fernando.core.Usuarios;
import br.com.fernando.server.application.UsuariosServiceInterface;

public class UsuariosServiceIT {

    public static String SERVICES_CONTEXT = "http://localhost:8080/soa-aplicado/services";

    public static String USUARIOS_CONTEXT = SERVICES_CONTEXT + "/usuarios";

    private byte[] fotoSaudate;

    @Before
    public void setup() throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(UsuariosServiceIT.class.getResourceAsStream("/saudate.png"));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        this.fotoSaudate = baos.toByteArray();
    }

    @Test
    public void testeRecepcaoImagens() throws Exception {

        final Client client = ClientBuilder.newClient();

        final WebTarget usuariosTarget = client.target(USUARIOS_CONTEXT).path("/{id}").resolveTemplate("id", 1);

        final Invocation usuariosInvocation = usuariosTarget.request("image/*").buildGet();

        final Response response = usuariosInvocation.invoke();

        Assert.assertEquals(200, response.getStatus());

        final byte[] image = response.readEntity(byte[].class);

        Assert.assertArrayEquals(fotoSaudate, image);

        final String descricao = (String) response.getHeaders().getFirst(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM);

        Assert.assertEquals("Alexandre Saudate - 2012", descricao);

    }

    @Test
    public void testeCriacaoImagens() throws Exception {

        // SslConfigurator sslConfig = SslConfigurator.newInstance()
        // .trustStoreFile("./truststore_client")
        // .trustStorePassword("secret-password-for-truststore")
        // .keyStoreFile("./keystore_client")
        // .keyPassword("secret-password-for-keystore");
        //
        // SSLContext sslContext = sslConfig.createSSLContext();
        // Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();

        // equest().property(HTTP_AUTHENTICATION_BASIC_USERNAME, "homer").property(HTTP_AUTHENTICATION_BASIC_PASSWORD, "p1swd745")

        final Client client = ClientBuilder.newClient();

        final WebTarget usuariosTarget = client.target(USUARIOS_CONTEXT).path("/{id}").resolveTemplate("id", 1);

        final Entity<byte[]> entity = Entity.entity(fotoSaudate, "image/*");

        final Invocation usuariosInvocation = usuariosTarget.request("image/*").header(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM, "Nova descricao").buildPut(entity);
        // //
        final Response response = usuariosInvocation.invoke();

        // final Response response = usuariosTarget.request("image/*").header(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM, "Nova descricao").put(entity);

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testeRecepcaoUsuarios() {
        // Obtaining the instance of Client which will be entry point to invoking REST Services.
        final Client client = ClientBuilder.newClient();

        // Targeting the RESTful Webserivce we want to invoke by capturing it in WebTarget instance.
        final WebTarget usuariosTarget = client.target(USUARIOS_CONTEXT);

        // Building the request i.e a GET request to the RESTful Webservice defined
        // by the URI in the WebTarget instance.
        final Invocation allBooksInvocation = usuariosTarget.request(MediaType.APPLICATION_XML).buildGet();

        // Invoking the request to the RESTful API and capturing the Response.
        final Response response = allBooksInvocation.invoke();

        Assert.assertEquals(200, response.getStatus());

        // As we know that this RESTful Webserivce returns the XML data which can be unmarshalled
        // into the instance of Books by using JAXB.
        final Usuarios usuarios = response.readEntity(Usuarios.class);

        Assert.assertNotNull(usuarios);
        Assert.assertNotNull(usuarios.getUsuarios());
        Assert.assertEquals(5, usuarios.getUsuarios().size());
    }

    @Test
    public void testeRecepcaoUsuariosClientRequest() throws Exception {

        // Obtaining the instance of Client which will be entry point to invoking REST Services.
        final Client client = ClientBuilder.newClient();

        // Targeting the RESTful Webserivce we want to invoke by capturing it in WebTarget instance.
        final WebTarget usuariosTarget = client.target(USUARIOS_CONTEXT).path("/{id}").resolveTemplate("id", 1);

        // Building the request i.e a GET request to the RESTful Webservice defined
        // by the URI in the WebTarget instance.
        final Invocation allBooksInvocation = usuariosTarget.request(MediaType.APPLICATION_XML).buildGet();

        // Invoking the request to the RESTful API and capturing the Response.
        final Response response = allBooksInvocation.invoke();

        Assert.assertEquals(200, response.getStatus());

        // As we know that this RESTful Webserivce returns the XML data which can be unmarshalled
        // into the instance of Books by using JAXB.
        final Usuario usuario = response.readEntity(Usuario.class);

        Assert.assertNotNull(usuario);
    }
}
