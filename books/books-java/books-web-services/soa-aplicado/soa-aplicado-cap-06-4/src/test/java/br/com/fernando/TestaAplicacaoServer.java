package br.com.fernando;

public class TestaAplicacaoServer {

    public static void main(final String[] args) {

        // -------- Servidor ---------------------------------------------------------------------------------------------

        final int httpPort = 8080;
        final String appName = "soa-aplicado";

        // Criando o pacote
        final EmbeddedWar war = new EmbeddedWar(appName, "src/test/resources/webapp");
        war.addClassesFromPackage("br.com.fernando", true);
        war.addLibsFromMavenPom("pom.xml");

        // Iniciando o servidor glassfish
        final EmbeddedJettyServer embeddeJettyServer = new EmbeddedJettyServer();

        embeddeJettyServer.start(httpPort);
        embeddeJettyServer.deploy(appName, war.exportToFile("target").getAbsolutePath());

        System.out.println("Construido!");

        // http://localhost:8080/soa-aplicado/livroWebService?wsdl
        //
        // wsimport -keep -d /home/fernando/workspace/soa-aplicado/soa-aplicado-cap-06-4/src/main/java/br/com/fernando/client -Xnocompile -verbose /home/fernando/workspace/soa-aplicado/soa-aplicado-cap-06-4/src/main/resources/livroWebService.wsdl
        //

        embeddeJettyServer.stop();

        // -------- Cliente ---------------------------------------------------------------------------------------------

    }
}
