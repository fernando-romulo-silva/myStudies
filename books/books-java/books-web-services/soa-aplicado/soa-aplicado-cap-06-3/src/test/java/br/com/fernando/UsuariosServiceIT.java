package br.com.fernando;

//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.Credentials;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.jboss.resteasy.client.ClientExecutor;
//import org.jboss.resteasy.client.ClientRequest;
//import org.jboss.resteasy.client.ClientResponse;
//import org.jboss.resteasy.client.ProxyFactory;
//import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
//import org.jboss.resteasy.util.Base64;

//import com.knight.usuarios.modelos.Usuario;
//import com.knight.usuarios.modelos.Usuarios;
//import com.knight.usuarios.servicos.UsuariosServiceInterface;

public class UsuariosServiceIT {

    public static String SERVICES_CONTEXT = "https://localhost:8443/soa-cap06-usuarios-0.0.1-SNAPSHOT/services";

    public static String USUARIOS_CONTEXT = SERVICES_CONTEXT + "/usuarios";

    public static String USUARIO_ADMIN = "admin";

    public static String SENHA_ADMIN = "admin";

    private byte[] fotoSaudate;

    // @Before
    // public void setup() throws IOException {
    // final BufferedImage bufferedImage = ImageIO.read(UsuariosServiceIT.class.getResourceAsStream("/saudate.png"));
    // final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // ImageIO.write(bufferedImage, "png", baos);
    // this.fotoSaudate = baos.toByteArray();
    // }
    //
    // @Test
    // public void testeRecepcaoImagens() throws Exception {
    // final ClientResponse<byte[]> clientResponse = new ClientRequest(USUARIOS_CONTEXT + "/{id}").pathParameters(1).accept("image/*").header("Authorization", criaAutenticacao(USUARIO_ADMIN, SENHA_ADMIN)).get(byte[].class);
    // final byte[] image = clientResponse.getEntity();
    // final int status = clientResponse.getStatus();
    //
    // Assert.assertEquals(200, status);
    // Assert.assertArrayEquals(fotoSaudate, image);
    //
    // final String descricao = clientResponse.getHeaders().getFirst(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM);
    //
    // Assert.assertEquals("Alexandre Saudate - 2012", descricao);
    // }
    //
    // @Test
    // public void testeCriacaoImagens() throws Exception {
    // ClientResponse<?> clientResponse = new ClientRequest(USUARIOS_CONTEXT + "/{id}").pathParameters(1).header(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM, "Nova descricao")
    //
    // .header("Authorization", criaAutenticacao(USUARIO_ADMIN, SENHA_ADMIN))
    //
    // .body("image/png", fotoSaudate).put();
    //
    // Assert.assertEquals(204, clientResponse.getStatus());
    // try {
    // clientResponse = new ClientRequest(USUARIOS_CONTEXT + "/{id}").pathParameters(1).accept("image/*").header("Authorization", "Basic " + Base64.encodeBytes("admin:admin".getBytes())).get(byte[].class);
    // final byte[] image = (byte[]) clientResponse.getEntity();
    // final int status = clientResponse.getStatus();
    //
    // Assert.assertEquals(200, status);
    // Assert.assertArrayEquals(fotoSaudate, image);
    //
    // final String descricao = clientResponse.getHeaders().getFirst(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM);
    //
    // Assert.assertEquals("Nova descricao", descricao);
    // } finally {
    // // ajusta o estado de volta para o que estava anteriormente
    // clientResponse = new ClientRequest(USUARIOS_CONTEXT + "/{id}").pathParameters(1).header(UsuariosServiceInterface.CAMPO_DESCRICAO_IMAGEM, "Alexandre Saudate - 2012")
    //
    // .header("Authorization", criaAutenticacao(USUARIO_ADMIN, SENHA_ADMIN))
    //
    // .body("image/png", fotoSaudate).put();
    //
    // }
    //
    // }
    //
    // @Test
    // public void testeRecepcaoUsuarios() {
    //
    // final Credentials credentials = new UsernamePasswordCredentials(USUARIO_ADMIN, SENHA_ADMIN);
    // final DefaultHttpClient httpClient = new DefaultHttpClient();
    // httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
    //
    // final ClientExecutor clientExecutor = new ApacheHttpClient4Executor(httpClient);
    //
    // final UsuariosServiceInterface usuariosService = ProxyFactory.create(UsuariosServiceInterface.class, SERVICES_CONTEXT, clientExecutor);
    //
    // @SuppressWarnings("unchecked")
    // final ClientResponse<Usuarios> response = (ClientResponse<Usuarios>) usuariosService.listarUsuarios(null, null, null, null);
    //
    // Assert.assertEquals(200, response.getStatus());
    //
    // final Usuarios usuarios = response.getEntity(Usuarios.class);
    //
    // Assert.assertNotNull(usuarios);
    // Assert.assertNotNull(usuarios.getUsuarios());
    // Assert.assertEquals(5, usuarios.getUsuarios().size());
    // }
    //
    // @Test
    // public void testeRecepcaoUsuariosClientRequest() throws Exception {
    // final ClientResponse<Usuario> response = new ClientRequest(USUARIOS_CONTEXT + "/{id}").pathParameters(1).header("Authorization", criaAutenticacao(USUARIO_ADMIN, SENHA_ADMIN)).get(Usuario.class);
    //
    // final Usuario usuario = response.getEntity();
    //
    // Assert.assertNotNull(usuario);
    // }
    //
    // private static String criaAutenticacao(final String usuario, final String senha) {
    // return "Basic " + encodeUsuarioSenha(usuario, senha);
    // }
    //
    // private static String encodeUsuarioSenha(final String usuario, final String senha) {
    // return Base64.encodeBytes((usuario + ":" + senha).getBytes());
    // }

}
