package br.com.fernando;

public class Principal {

    // Patterns SOA
    //
    // O Modelo Canônico
    //
    // O Modelo Canônico é um dos design patterns mais importantes de SOA. Ele sintetiza o foco de SOA na reusabilidade
    // e separação de responsabilidades, através de uma técnica bastante simples — que, apesar disso, é ignorada em projetos
    // puros de integração, sendo um dos pontos de divergência entre os dois modelos.
    // Para notar a existência e perceber a necessidade de um modelo canônico, tome o modelo de arquitetura que está sendo
    // desenvolvido: até agora, existe um sistema de gerenciamento de usuários e um sistema de gerenciamento de estoques.
    // Caso este sistema precise de algo relacionado a usuários, ele não desenvolve o próprio, mas reutiliza o que já existe
    // no sistema de usuários. Para promover esta separação de responsabilidades, utiliza-se o Modelo Canônico.
    // A técnica consiste, simplesmente, em manter XML Schemas separados por modelo de domínio para toda a empresa.
    //
    // Podemos gerar as classes pela ferramenta do eclipse ou usando o comando:
    // /usr/lib/jvm/java-7-oracle/bin/xjc -d ~/workspace/soa-aplicado-cap-07-1/src/main/java/ ~/workspace/soa-aplicado-cap-07-1/src/test/resources/webapp/WEB-INF/schemas/estoque_v1_0.xsd
    //
    // O Contract-Firts
    //
    // ma extensão natural do desenvolvimento do modelo canônico é a técnica conhecida como desenvolvimento contract-first. Esta técnica consiste em desenvolver os
    // WSDLs antes do código propriamente dito, dando ao desenvolvedor total controle sobre o tráfego de dados.
    // Como você viu no capítulo 2, existem várias seções a serem tratadas em WSDLs, e lidar com elas não é exatamente
    // fácil. No entanto, como você pode conferir no capítulo 6, pode ser necessário obter total domínio sobre os
    // WSDLs para não deixar que eles te dominem.
    // Obviamente, também não é necessário desenvolver todo o conteúdo do WSDL manualmente - podemos utilizar a IDE para ajudar neste sentido.
    // Podemos gerar as classses pelo comando:
    //
    // /usr/lib/jvm/java-7-oracle/bin/wsimport -s ~/workspace/soa-aplicado-cap-07-1/src/main/java/ ~/workspace/soa-aplicado-cap-07-1/src/test/resources/webapp/WEB-INF/wsdl/autores.wsdl
    //
    // Veja os arquivos 'autores.wsdl', estoque_v1_0.xsd e AutoresServiceImpl para implementacao dos padroes canonico e contract first

}
