package br.com.fernando;

public class TestaAplicacao {

    public static void main(final String[] args) {

        final int httpPort = 8080;
        final String appName = "soa-aplicado";

        // Criando o pacote
        final EmbeddedWar war = new EmbeddedWar(appName, "src/test/resources/webapp");
        war.addClassesFromPackage("br.com.fernando.server", true);
        war.addClassesFromPackage("br.com.fernando.core", true);
        war.addLibsFromMavenPom("pom.xml");

        // Iniciando o servidor glassfish
        final EmbeddedGlassfishServer embeddedGlassfishServer = new EmbeddedGlassfishServer();

        embeddedGlassfishServer.start(httpPort);
        embeddedGlassfishServer.deploy(appName, war.exportToFile("target").getAbsolutePath());

    }
}
