package org.nandao.cap09.p03UsingTheExtendedSSLSessionInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestClient {

    public static void main(String[] args) throws Exception {

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 9999);

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        OutputStream outputStream = sslSocket.getOutputStream();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedwriter = new BufferedWriter(outputStreamWriter);
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            bufferedwriter.write(line + '\n');
            bufferedwriter.flush();
        }
    }

}
