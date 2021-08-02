package br.com.fernando;

public class Principal {

    // Veja as classes 'UsuariosService' e 'UsuariosServiceInterface' e o arquivo 'web.xml' para ver a utilizacao de servicos REST.
    //
    //
    // REST é uma sigla que significa Representational State Transfer. REST é um modelo
    // arquitetural concebido por um dos autores do protocolo HTTP (o doutor Roy Fielding), e
    // tem como plataforma justamente as capacidades do protocolo, onde se destacam:
    // • Diferentes métodos (ou verbos) de comunicação ( GET , POST , PUT , DELETE ,HEAD , OPTIONS );
    // • Utilização de headers HTTP (tanto padronizados quanto customizados);
    // • Definição de arquivos como recursos (ou seja, cada um com seu próprio endereço);
    // • Utilização de media types.

    // Relação do HTTP com REST:
    // REST é fortemente ligado ao HTTP, a ponto de não poder ser executado com
    // sucesso em outros protocolos. Ele é baseado em diversos princípios que fizeram
    // com que a própria web fosse um sucesso. Estes princípios são:
    // • URLs bem definidas para recursos;
    // • Utilização dos métodos HTTP de acordo com seus propósitos;
    // • Utilização de media types efetiva;
    // • Utilização de headers HTTP de maneira efetiva;
    // • Utilização de códigos de status HTTP;
    // • Utilização de Hipermídia como motor de estado da aplicação.

    // Veja a URL Rest:
    //
    // /validador/cpf/12345678909
    //
    // Note que, assumindo esta mesma orientação, é possível definir URLs para a maioria dos problemas.
    // Voltando ao problema de nossos usuários, por exemplo, note que, se temos uma URL
    // /usuarios/1 , /usuarios é nosso recurso principal e 1 é o identificador do nosso recurso
    // (e não temos quaisquer subtipos mapeados).
    // No entanto, se quisermos, por exemplo, apenas o endereço de um determinado usuário,
    // o senso comum ditaria que teríamos uma URL /usuarios/endereco/1 , certo?
    // Errado.
    // Note que as URLs seguem uma estrutura hierárquica, ou seja, o elemento seguinte
    // obedece a um relacionamento com o elemento anterior. Ou seja, no exemplo dos endereços dos usuários,
    // devemos obter primeiro o usuário em questão e, depois, o endereço.
    // Assim sendo, a URL correta seria /usuarios/1/endereco . No entanto,
    // se quiséssemos obter apenas o endereço de todos os usuários, aí teríamos uma
    // URL /usuarios/endereco — endereco obedece a uma estrutura hierárquica em relação a usuários

    // Um fato curioso eh que o a url do web service mudou, 'http://localhost:8080/soa-aplicado/livros' de passou para 'http://soa-aplicado:8080/livros/LivrosService'
    // acho q falta alguma configuracao do WSDL;

}
