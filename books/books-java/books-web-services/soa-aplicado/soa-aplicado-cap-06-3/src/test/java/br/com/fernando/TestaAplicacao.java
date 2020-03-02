package br.com.fernando;

public class TestaAplicacao {

    public static void main(final String[] args) {

        final int httpPort = 8080;
        final String appName = "soa-aplicado";

        final String dataSourceName = appName + "DS";

        // parametros iniciais Banco
        final String dataBaseUserName = "root";
        final String dataBaseUserPassword = "root";
        final int dataBasePort = 2527;
        final String dataBaseName = appName + "DB";
        final String dataBaseHost = "localhost";
        final String dataBaseDriverClassName = "org.hsqldb.jdbcDriver";
        final String dataBaseDriverIdentifier = "jdbc:hsqldb:hsql";

        final String dataBaseUrl = dataBaseDriverIdentifier + "://" + dataBaseHost + ":" + dataBasePort + "/" + dataBaseName;

        // final Iniciando o servidor final de Banco final de Dados
        final EmbeddedHsqldbServer embeddedHsqldbServer = new EmbeddedHsqldbServer();
        embeddedHsqldbServer.addDataBase(dataBaseName, dataBaseUserName, dataBaseUserPassword);
        embeddedHsqldbServer.start(dataBasePort);

        // Criando o pacote
        final EmbeddedWar war = new EmbeddedWar(appName, "src/test/resources/webapp");
        war.addClassesFromPackage("br.com.fernando.server", true);
        war.addClassesFromPackage("br.com.fernando.core", true);
        war.addMetaInfFiles(Resource.createResource("persistence.xml", "src/main/resources/persistence.xml"));
        war.addLibsFromMavenPom("pom.xml");

        // Iniciando o servidor glassfish
        final EmbeddedGlassfishServer embeddedGlassfishServer = new EmbeddedGlassfishServer();
        embeddedGlassfishServer.addDataSource(dataSourceName, dataBaseDriverClassName, dataBaseUrl, dataBaseUserName, dataBaseUserPassword);

        embeddedGlassfishServer.start(httpPort);
        embeddedGlassfishServer.deploy(appName, war.exportToFile("target").getAbsolutePath());

        embeddedHsqldbServer.executeFileScripts(dataBaseName, dataBaseUserName, dataBaseUserPassword, "src/main/resources/import.sql");

    }
}
