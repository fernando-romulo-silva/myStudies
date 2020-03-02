package br.com.fernando.server.application.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RSA")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChaveRSA {

    private BigInteger modulus;

    private BigInteger publicExponent;

    public static ChaveRSA carregar() throws IOException, ClassNotFoundException {

        try (InputStream inputStream = ChaveRSA.class.getResourceAsStream("src/main/resources/public.key")) {
            final ObjectInputStream ois = new ObjectInputStream(inputStream);
            final RSAPublicKey rsaPublicKey = (RSAPublicKey) ois.readObject();
            final ChaveRSA chaveRSA = new ChaveRSA();
            chaveRSA.modulus = rsaPublicKey.getModulus();
            chaveRSA.publicExponent = rsaPublicKey.getPublicExponent();
            return chaveRSA;
        }
    }

    public final BigInteger getModulus() {
        return modulus;
    }

    public final BigInteger getPublicExponent() {
        return publicExponent;
    }
}
