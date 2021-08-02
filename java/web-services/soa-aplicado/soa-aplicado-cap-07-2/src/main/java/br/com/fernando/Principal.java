package br.com.fernando;

public class Principal {

    // Serviços assíncronos com WS-Addressing
    //
    // Muitas vezes, pode ser necessário invocar uma operação cujo resultado não pode
    // ser oferecido imediatamente. Estes são o caso de relatórios emitidos pelo sistema.
    // Quando um volume de informações muito grande deve ser levantado, geralmente é
    // mais interessante realizar a operação de maneira assíncrona, ou seja, o cliente faz
    // a requisição e recebe imediatamente uma resposta dizendo que a requisição será
    // processada. O servidor, então, inicia o processamento e quando terminar, invoca
    // o solicitante.
    // A especificação responsável por prover facilidades relacionadas ao endereça-
    // mento de mensagens é a WS-Addressing . Com esta especificação, é possível redi-
    // recionar mensagens, e é a partir desta que podemos informar ao servidor qual o en-
    // dereço para resposta de uma determinada operação. Assim como a WS-Security,
    // a WS-Addressing é ativada utilizando WS-Policy como apoio. No entanto, as
    // alterações realizadas pela WS-Addressing vão permear todo o WSDL.
    //
    // /usr/lib/jvm/java-7-oracle/bin/wsimport -s ~/workspace/soa-aplicado-cap-07-2/src/main/java/ ~/workspace/soa-aplicado-cap-07-2/src/test/resources/webapp/WEB-INF/wsdl/autores.wsd
    // /usr/lib/jvm/java-7-oracle/bin/wsimport -s ~/workspace/soa-aplicado-cap-07-2/src/main/java/ ~/workspace/soa-aplicado-cap-07-2/src/test/resources/webapp/WEB-INF/wsdl/autoresCallback.wsd

    // Veja os arquivos 'autores.wsdl', 'autoresCallback.wsdl' e AddressingHandler para mensagens assincronas com WS
}
