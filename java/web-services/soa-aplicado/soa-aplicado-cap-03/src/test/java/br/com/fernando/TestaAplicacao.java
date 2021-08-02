package br.com.fernando;

public class TestaAplicacao {

    public static void main(final String[] args) {

        final int httpPort = 8080;
        final String appName = "soa-aplicado";

        // final String dataSourceName = appName + "DS";
        //
        // // parametros iniciais Banco
        // final String dataBaseUserName = "root";
        // final String dataBaseUserPassword = "root";
        // final int dataBasePort = 2527;
        // final String dataBaseName = appName + "DB";
        // final String dataBaseHost = "localhost";
        // final String dataBaseDriverClassName = "org.hsqldb.jdbcDriver";
        // final String dataBaseDriverIdentifier = "jdbc:hsqldb:hsql";

        // "jdbc:hsqldb:hsql://localhost:2527/cdiIntegreDependenciasContextoDB";
        // final String dataBaseUrl = dataBaseDriverIdentifier + "://" + dataBaseHost + ":" + dataBasePort + "/" + dataBaseName;

        // Iniciando o servidor de Banco de Dados
        // final EmbeddedHsqldbServer embeddedHsqldbServer = new EmbeddedHsqldbServer();
        // embeddedHsqldbServer.addDataBase(dataBaseName, dataBaseUserName, dataBaseUserPassword);
        // embeddedHsqldbServer.startServer(dataBasePort);

        // Iniciando o servidor glassfish

        final EmbeddedWar war = new EmbeddedWar(appName, "src/test/resources/webapp");
        war.addClassesFromPackage("br.com.fernando.server", true);
        war.addClassesFromPackage("br.com.fernando.core", true);
        war.addLibsFromMavenPom("pom.xml");

        final EmbeddedGlassfishServer embeddedGlassfishServer = new EmbeddedGlassfishServer();
        embeddedGlassfishServer.start(httpPort);
        embeddedGlassfishServer.deploy(appName, war.exportToFile("target").getAbsolutePath());
    }
}
