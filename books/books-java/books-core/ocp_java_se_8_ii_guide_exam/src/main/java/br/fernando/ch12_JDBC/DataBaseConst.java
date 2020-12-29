package br.fernando.ch12_JDBC;

import org.myembedded.rdbms.MyEmbeddedRdbms;

public class DataBaseConst {

    public static final int PORT = 2527;

    public static final String NAME = "embeddedRdbmsTest";

    public static final String URL = "jdbc:hsqldb:hsql://localhost:" + PORT + "/" + NAME;

    public final static String LOGIN = "root";

    public final static String PASSWORD = "root";

    public static void example() {

        try (final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms();) {

            myEmbeddedRdbms.addDataBase(NAME, LOGIN, PASSWORD);
            myEmbeddedRdbms.start(PORT);

            myEmbeddedRdbms.executeFileScripts(NAME, LOGIN, PASSWORD, "src/main/resources/script.sql");
        }

    }

    public static void main(String[] args) {
        example();
    }

}
