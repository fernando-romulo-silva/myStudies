package br.com.nandao.cap10NovaApiDatas.part03;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

// Enums no lugar de constantes
public class Test {

    public static void main(String[] args) {

        // Essa nova API de datas favorece o uso de Enums no lugar das famosas constantes
        // do Calendar. Para representar um mês, por exemplo, podemos utilizar o enum Month.

        System.out.println(LocalDate.of(2014, 12, 25));
        System.out.println(LocalDate.of(2014, Month.DECEMBER, 25));

        // Outra vantagem de utilizar os enums são seus diversos métodos auxiliares. Note
        // como é simples consultar o primeiro dia do trimestre de determinado mês, ou então
        // incrementar/decrementar meses:

        System.out.println(Month.DECEMBER.firstMonthOfQuarter());
        System.out.println(Month.DECEMBER.plus(2));
        System.out.println(Month.DECEMBER.minus(1));

        // Para imprimir o nome de um mês formatado, podemos utilizar o método
        // getDisplayName fornecendo o estilo de formatação (completo, resumido, entre
        // outros) e também o Locale:

        final Locale pt = new Locale("pt");
        System.out.println(Month.DECEMBER.getDisplayName(TextStyle.FULL, pt));
        System.out.println(Month.DECEMBER.getDisplayName(TextStyle.SHORT, pt));
    }
}
