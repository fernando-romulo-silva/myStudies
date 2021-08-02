package br.com.fernando.cap10_Lidando_com_excecoes;


// Diferencie entre exceções do tipo checked, runtime e erros
public class Test01 {

    // A classe principal dessa hierarquia é a classe Throwable. 
    // Qualquer erro de execução é um objeto dessa classe ou de uma que deriva dela.
    // Como filhas diretas de Throwable temos: Error e Exception .
    //
    // Os Error s são erros de execução gerados por uma situação totalmente
    // anormal que não deveria ser prevista pela aplicação. Por exemplo, um
    // OutOfMemoryError é gerado quando a JVM não tem mais memória RAM
    // disponível para oferecer para as aplicações. Em geral, esse tipo de erro não
    // é responsabilidade das aplicações pois quem cuida do gerenciamento de memória é a JVM.
    //
    // Por sua vez, as Exception s são divididas em duas categorias: as uncheckeds e as checkeds.
    // 
    // As uncheckeds são exceptions que teoricamente podem ser mais facilmente evitadas pelo próprio 
    // programador se ele codificar de maneira mais cuidadosa.
    //
    // As checkeds são exceptions que teoricamente não são fáceis de evitar, de modo que a melhor 
    // abordagem é estar sempre preparado para seu acontecimento.
    // 
    // O compilador irá verificar se seu programa pode lançar alguma checked exception e, 
    // neste caso, obrigá-lo a tratar essa exception de alguma maneira. No caso das exceptions unchecked,
    // não há nenhuma verificação por parte do compilador pelo tratamento ou não.
    
}
