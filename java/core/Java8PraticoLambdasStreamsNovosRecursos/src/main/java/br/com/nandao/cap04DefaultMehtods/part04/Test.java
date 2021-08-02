package br.com.nandao.cap04DefaultMehtods.part04;

// Herança múltipla?
public class Test {

    // Métodos defaults foram adicionados para permitir que interfaces evoluam sem quebrar código existente. 
    // Essa é uma das frases mais repetidas na lista de discussão  da especificação.
    // Eles não foram criados para permitir alguma variação de herança múltipla ou de mixins. 
    // Vale lembrar que há uma série de restrições para esses métodos. 
    // Em  especial, eles não podem acessar atributos de instância, até porque isso não existe
    // em interfaces! Em outras palavras, não há herança múltipla ou compartilhamento  de estado.
}
