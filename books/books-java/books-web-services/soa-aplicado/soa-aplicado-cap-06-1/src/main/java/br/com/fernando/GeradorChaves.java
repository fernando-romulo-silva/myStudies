package br.com.fernando;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * 
 * Rode o método main() contido nesta classe para obter um par de chaves que atendem o algoritmo RSA (comprimento 2048 bytes). Dois arquivos serão gerados: um chamado private.key (contendo a chave privada) e outro chamado public.key (contendo a chave
 * pública). Não se esqueça de que a chave pública encripta dados e a chave privada decripta - e o que uma chave encripta, somente a outra decripta. Portanto, lembre-se de guardar estas chaves em local seguro.
 * 
 * @author Alexandre Saudate
 * 
 */
public class GeradorChaves {

    public static void main(final String[] args) throws Exception {
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);

        final KeyPair kp = kpg.generateKeyPair();
        final Key publicKey = kp.getPublic();
        final Key privateKey = kp.getPrivate();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/test/resources/public.key"));
        oos.writeObject(publicKey);
        oos.flush();
        oos.close();

        oos = new ObjectOutputStream(new FileOutputStream("src/test/resources/private.key"));
        oos.writeObject(privateKey);
        oos.flush();
        oos.close();

    }

}
