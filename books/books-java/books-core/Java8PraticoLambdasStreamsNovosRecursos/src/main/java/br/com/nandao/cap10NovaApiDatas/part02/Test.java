package br.com.nandao.cap10NovaApiDatas.part02;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

// Trabalhando com datas de forma fluente
public class Test {

    public static void main(String[] args) {
        final Calendar mesQueVem1 = Calendar.getInstance();
        mesQueVem1.add(Calendar.MONTH, 1);

        System.out.println();
        System.out.println(mesQueVem1);

        // Com a nova API de datas podemos fazer essa mesma operação de uma forma
        // mais moderna, utilizando sua interface fluente:

        final LocalDate mesQueVem2 = LocalDate //
            .now() //
            .plusMonths(1);

        System.out.println();
        System.out.println(mesQueVem2);

        // Como a maior parte de seus métodos não possibilita o retorno de parâmetros
        // nulos, podemos encadear suas chamadas de forma fluente sem a preocupação de
        // receber um NullPointerException!
        // Da mesma forma que fizemos para adicionar o mês, podemos utilizar os mé-
        // todos plusDays, plusYears em diante de acordo com nossa necessidade. E de
        // forma igualmente simples, conseguimos decrementar os valores utilizando os mé-
        // todos minus presentes nesses novos modelos. Para subtrair um ano, por exemplo,
        // faríamos:

        final LocalDate anoPassado1 = LocalDate.now().minusYears(1);
        System.out.println();
        System.out.println(anoPassado1);

        // Um ponto importante é que a classe LocalDate representa uma data sem horá-
        // rio nem timezone, por exemplo 25-01-2014. Se as informações de horário forem
        // importantes, usamos a classe LocalDateTime de forma bem parecida:

        final LocalDateTime agora1 = LocalDateTime.now();
        System.out.println(agora1);

        // Há ainda o LocalTime para representar apenas as horas:
        final LocalTime agora2 = LocalTime.now();
        System.out.println(agora2);

        // Uma outra forma de criar uma LocalDateTime com horário específico é utilizando
        // o método atTime da classe LocalDate, repare:
        final LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12, 0);
        System.out.println(hojeAoMeioDia);

        // Além disso, as classes dessa nova API contam com o método estático of, que é
        // um factory method para construção de suas novas instâncias:
        final LocalDate date = LocalDate.of(2014, 12, 25);
        System.out.println(date);

        final LocalDateTime dateTime = LocalDateTime.of(2014, 12, 25, 10, 30);
        System.out.println(dateTime);

        // Repare que todas essas invocações não só podem como devem ser encadeadas
        // por um bom motivo: o modelo do java.time é imutável! Cada operação devolve
        // um novo valor, nunca alterando o valor interno dos horários, datas e intervalos utilizados
        // na operação. Isso simplifica muita coisa, não apenas para trabalhar concorrentemente.
        // Repare:
        final LocalDate dataDoPassado = LocalDate.now().withYear(1988);
        System.out.println(dataDoPassado);

        // Mas e para recuperar essas informações? Podemos utilizar seus métodos gets,
        // de acordo com o valor que estamos procurando. Por exemplo, o getYear para
        // saber o ano, ou getMonth para o mês, assim por diante.
        final LocalDate dataDoPassado2 = LocalDate.now().withYear(1988);
        System.out.println(dataDoPassado2.getYear());

        // Existem também outros comportamentos essencias, como saber se alguma medida
        // de tempo acontece antes, depois ou ao mesmo tempo que outra. Para esses
        // casos, utilizamos os métodos is:

        final LocalDate hoje1 = LocalDate.now();
        final LocalDate amanha1 = LocalDate.now().plusDays(1);

        System.out.println(hoje1.isBefore(amanha1));
        System.out.println(hoje1.isAfter(amanha1));
        System.out.println(hoje1.isEqual(amanha1));

        // Há ainda os casos em que queremos comparar datas iguais, porém em
        // timezones diferentes. Utilizar o método equals, nesse caso, não causaria o efeito
        // esperado — claro, afinal a sobrescrita desse método na classe ZonedDateTime espera
        // que o offset entre as datas seja o mesmo:

        ZonedDateTime tokyo1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));
        
        final ZonedDateTime saoPaulo1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));
        
        System.out.println(tokyo1.isEqual(saoPaulo1));
        
        // Não muito diferente do que podemos esperar, o resultado ainda será false.
        // Apesar da semelhança entre as datas, elas estão em timezones diferentes, portanto
        // não são iguais. Para que o resultado do método isEqual de um ZonedDateTime
        // seja true, precisamos acertar a diferença de tempo entre as duas datas. Uma forma
        // de fazer isso seria adicionar as 12 horas de diferença na instância de tokyo, repare:
        
        tokyo1 = tokyo1.plusHours(12);
        System.out.println(tokyo1.isEqual(saoPaulo1));
        
    }
}
