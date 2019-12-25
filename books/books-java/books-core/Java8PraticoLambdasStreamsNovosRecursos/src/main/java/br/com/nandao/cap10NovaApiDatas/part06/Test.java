package br.com.nandao.cap10NovaApiDatas.part06;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

// Duração e Período
public class Test {

    public static void test1() {
        // Por exemplo, como calcular a diferença de dias entre dois Calendars?
        // Você possivelmente já passou por isso:

        final Calendar agora = Calendar.getInstance();
        final Calendar outraData = Calendar.getInstance();
        outraData.set(1988, Calendar.JANUARY, 25);
        final long diferenca = agora.getTimeInMillis() - outraData.getTimeInMillis();
        final long milissegundosDeUmDia = 1000 * 60 * 60 * 24;
        final long dias = diferenca / milissegundosDeUmDia;

        System.out.println(dias);
    }

    public static void test2() {
        // Agora podemos fazer essa mesma operação de forma muito mais simples, utilizando
        // o enum ChronoUnit da nova api:

        final LocalDate agora = LocalDate.now();
        final LocalDate outraData = LocalDate.of(1989, Month.JANUARY, 25);
        final long dias = ChronoUnit.DAYS.between(outraData, agora);

        System.out.println(dias);

        // Esse enum está presente no pacote java.time.temporal e possui uma representação
        // para cada uma das diferentes medidas de tempo e data. Além disso,
        // possui vários métodos para facilitar o calculo de diferença entre seus valores e que
        // nos auxiliam a extrair informações úteis, como é o caso do between.
    }

    public static void test3() {

        // Mas e se também quisermos saber a diferença de anos e meses entre essas duas
        // datas? Poderíamos utilizar o ChronoUnit.YEARS e ChronoUnit.MONTHS para
        // obter essas informações, mas ele vai calcular cada uma das medidas de forma separada.
        // Repare:

        final LocalDate agora = LocalDate.now();
        final LocalDate outraData = LocalDate.of(1989, Month.JANUARY, 25);

        final long dias = ChronoUnit.DAYS.between(outraData, agora);
        final long meses = ChronoUnit.MONTHS.between(outraData, agora);
        final long anos = ChronoUnit.YEARS.between(outraData, agora);
        System.out.printf("%s dias, %s meses e %s anos", dias, meses, anos);
    }

    public static void test4() {
        // Uma forma de conseguir o resultado que esperamos: os dias, meses e anos entre
        // duas datas, é utilizando o modelo Period. Essa classe da API também possui o
        // método between, que recebe duas instâncias de LocalDate:
        final LocalDate agora1 = LocalDate.now();
        final LocalDate outraData1 = LocalDate.of(1989, Month.JANUARY, 25);
        final Period periodo1 = Period.between(outraData1, agora1);

        System.out.printf("%s dias, %s meses e %s anos", periodo1.getDays(), periodo1.getMonths(), periodo1.getYears());

        // A classe Period tem uma série de métodos que auxiliam nas diversas situações
        // que enfrentamos ao trabalhar com datas. Por exemplo, ao calcular uma diferença
        // entre datas, é comum a necessidade de lidarmos com valores negativos. Observe o
        // que acontece se alterarmos o ano da outraData para 2015:

        final LocalDate agora2 = LocalDate.now();
        final LocalDate outraData2 = LocalDate.of(2015, Month.JANUARY, 25);
        final Period periodo2 = Period.between(outraData2, agora2);
        System.out.println();
        System.out.printf("%s dias, %s meses e %s anos", periodo2.getDays(), periodo2.getMonths(), periodo2.getYears());

        // Essa pode ser a saída esperada, mas caso não seja, podemos facilmente perguntar
        // ao Period se ele é um período de valores negativos invocando o método
        // isNegative. Caso seja, poderíamos negar seus valores com o método negated, repare:

        Period periodo = Period.between(outraData2, agora2);

        if (periodo.isNegative()) {
            periodo = periodo.negated();
        }

        System.out.println();
        System.out.printf("%s dias, %s meses e %s anos", periodo.getDays(), periodo.getMonths(), periodo.getYears());
    }

    public static void test5() {
        // Existem diversas outras formas de se criar um Period, além do método
        // between. Uma delas é utilizando o método of(years, months, days) de
        // forma fluente:

        final Period periodo = Period.of(2, 10, 5);

        System.out.println(periodo);

        // Mas como criar um período de horas, minutos ou segundos? A resposta é:
        // não criamos. Neste caso, estamos interessados em outra medida de tempo, que é
        // a Duration. Enquanto um Period considera as medidas de data (dias, meses e
        // anos), a Duration considera as medidas de tempo (horas, minutos, segundos etc.).
        // Sua API é muito parecida, observe:

        final LocalDateTime agora = LocalDateTime.now();
        final LocalDateTime daquiAUmaHora = LocalDateTime.now().plusHours(1);

        Duration duration = Duration.between(agora, daquiAUmaHora);

        if (duration.isNegative()) {
            duration = duration.negated();
        }

        System.out.printf("%s horas, %s minutos e %s segundos", duration.toHours(), duration.toMinutes(), duration.getSeconds());

    }

    public static void main(String[] args) {
        test4();
    }

}
