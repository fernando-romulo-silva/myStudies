package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Aplique modificadores de acesso
public class Test06 {

    // O Java possui quatro modificadores de acesso:
    // • public ;
    // • protected ;
    // • Nenhum modificador, chamado de protected default ou private protected ;
    // • private .

    // Classes e interfaces cobradas na prova só aceitam os modificadores public ou default .
    //
    // Membros (construtores, métodos e variáveis) podem receber qualquer um dos quatro modificadores.
    //
    // Variáveis locais (declaradas dentro do corpo de um método ou construtor) e parâmetros não podem
    // receber nenhum modificador de acesso, mas podem receber outros modificadores.
    //
    // Public
    // O modificador public é o menos restritivo de todos. Classes, interfaces
    // e membros marcados com esse modificador podem ser acessados de qual-
    // quer componente, em qualquer pacote.
    //
    // Protected
    // Membros definidos com o modificador protected podem ser acessa dos por classes e interfaces no
    // mesmo pacote, e por qualquer classe que estenda aquela onde o membro foi definido,
    // independente do pacote.
    //
    // Default
    // Se não definirmos explicitamente qual o modificador de acesso, podemos dizer que aquele membro
    // está usando o modificador default , também chamado de package private .
    // Neste caso, os membros da classe só serão visíveis dentro do mesmo pacote,
    //
    // Private
    // private é o mais restritivo de todos os modificadores de acesso. Membros definidos como private
    // só podem ser acessados de dentro da classe e de nenhum outro lugar, independente de pacote ou herança.
}
