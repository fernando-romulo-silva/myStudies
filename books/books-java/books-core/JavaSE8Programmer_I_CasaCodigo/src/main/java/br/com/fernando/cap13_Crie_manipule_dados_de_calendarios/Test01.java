package br.com.fernando.cap13_Crie_manipule_dados_de_calendarios;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

// Crie e manipule dados de calendários usando as classes java.time.LocalDateTime,
// java.time.LocalDate, java.time.LocalTime, java.time.format.DateTimeFormatter, java.time.Period
public class Test01 {

    //
    public static void test01() {

        // Vamos entender quais classes e métodos foram incluídos, além de passar pelos detalhes que serão cobrados na prova.
        // As classes que serão cobradas são:
        //
        // • LocalDate: representa uma data sem hora no formato yyyy-MM-dd (ano-mês-dia).
        //
        // • LocalTime: representa uma hora no formato hh:mm:ss.zzz (hora:minuto:segundo.milissegundo).
        //
        // • LocalDateTime: representa uma data com hora no formato yyyy-MM-dd-HH-mm-ss.zzz (ano-mês-dia-hora-minutosegundo.milissegundo).
        //
        // • MonthDay: representa um dia e mês, sem o ano.
        //
        // • YearMonth: representa um mês e ano, sem o dia.
        //
        // • Period: representa um período de tempo, em dia, mês e ano.
        //
        // • DateTimeFormatter: classe que possui vários métodos para formatação.
        //
        // Todas as classes do pacote java.time são imutáveis, ou seja, após serem instanciadas,
        // seus valores não podem ser alterados, assim como a classe String.

        // Criando Datas
        LocalTime currentTime = LocalTime.now(); // 09:05:03.244
        LocalDate today = LocalDate.now(); // 2014-12-10
        LocalDateTime now = LocalDateTime.now(); // 2014-12-10-09-05-03.244

        // É possível escolher o fuso horário que será usando na criação das datas,
        // passando como parâmetro para o método now um objeto do tipo ZoneId:
        LocalTime time = LocalTime.now(ZoneId.of("America/Chicago"));
        LocalDate date = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));

        // Caso queira representar uma data ou hora específica, usamos o método
        // of. Cadaumdessesmétodos possui versões sobrecarregadas, recebendomais
        // oumenos valores iniciais. Todos os tipos de datas que contêmmeses possuem
        // versões de of, que recebem tanto números inteiros quanto valores do enum Month.
        //
        // Por exemplo, podemos criar uma representação do meio-dia:
        LocalTime noon = LocalTime.of(12, 0);

        // Para criar o natal de 2014 e de 2015:
        LocalDate christmas2014 = LocalDate.of(2014, 12, 25);
        LocalDate christmas2015 = LocalDate.of(2015, Month.DECEMBER, 25);

        // Podemos representar qualquer natal:
        MonthDay someChristmas = MonthDay.of(Month.DECEMBER, 31);

        // Ainda com o método of, podemos criar um momento exato no tempo:
        LocalDateTime someDate = LocalDateTime.of(2017, Month.JANUARY, 25, 13, 45);

        // Ou ainda passar um dia e somente adicionar o horário:
        LocalDateTime christmasAtNoon = LocalDateTime.of(christmas2014, noon);

        // Passar um valor inválido para qualquer um dos campos (mês 13, por exemplo) lançará um DateTimeException.
    }

    // Extraindo partes de uma data
    public static void test02() {
        // Para obter alguma porção de uma data, podemos usar os métodos precedidos por get:

        LocalDateTime now = LocalDateTime.of(2014, 12, 15, 13, 0);

        System.out.println(now.getDayOfMonth()); // 15
        System.out.println(now.getDayOfYear()); // 349
        System.out.println(now.getHour()); // 13
        System.out.println(now.getMinute()); // 0
        System.out.println(now.getYear()); // 2014
        System.out.println(now.getDayOfWeek()); // MONDAY
        System.out.println(now.getMonthValue()); // 12
        System.out.println(now.getMonth()); // DECEMBER

        // Além desses métodos, temos um método get(), que recebe como parâmetro
        // uma implementação da interface TemporalField, geralmente
        // ChronoField, e retorna um inteiro. Note que como estamos falando de um
        // campo que será retornado. Usamos ChronoField, um campo de tempo
        // cujo valor queremos saber:

        // 15
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));
        // 349
        System.out.println(now.get(ChronoField.DAY_OF_YEAR));
        // 13
        System.out.println(now.get(ChronoField.HOUR_OF_DAY));
        // 0
        System.out.println(now.get(ChronoField.MINUTE_OF_HOUR));
        // 2014
        System.out.println(now.get(ChronoField.YEAR));
        // 1 (MONDAY)
        System.out.println(now.get(ChronoField.DAY_OF_WEEK));
        // 12
        System.out.println(now.get(ChronoField.MONTH_OF_YEAR));

        // Nem todas as classes possuem todos os métodos. Por exemplo, objetos do
        // tipo LocalDate não possuem a parte de horas, logo não há métodos como getHour.
        // É necessário ficar atento ao tipo do objeto e a qual método está sendo chamado:
        LocalDate d = LocalDate.now();
        // d.getHour(); //compile error, method not found.
    }

    // Comparações entre datas
    public static void test03() {
        // Usamos os métodos que começam com "is" para realizar comparações entre as datas:

        MonthDay day1 = MonthDay.of(1, 1); // 01/jan
        MonthDay day2 = MonthDay.of(1, 2); // 02/jan

        System.out.println(day1.isAfter(day2)); // false
        System.out.println(day1.isBefore(day2)); // true

        // Além de métodos de comparação, também existem aqueles para indicar
        // se alguma porção da data é suportada pelo objeto:

        LocalDate aprilFools = LocalDate.of(2015, 4, 1);
        LocalDate foolsDay = LocalDate.of(2015, 4, 1);

        // are equals
        System.out.println(aprilFools.isEqual(foolsDay)); // true

        // does this object support days?
        System.out.println(aprilFools.isSupported(ChronoField.DAY_OF_MONTH)); // true

        // does this object supports hours?
        System.out.println(aprilFools.isSupported(ChronoField.HOUR_OF_DAY)); // false

        // Can I make operations with days?
        System.out.println(aprilFools.isSupported(ChronoUnit.DAYS)); // true

        // Can I make operations with hours?
        System.out.println(aprilFools.isSupported(ChronoUnit.HOURS)); // false
    }

    // Alterando as datas
    public static void test04() {

        // Todos os objetos da nova API de datas são imutáveis, ou seja, não podem
        // ter o seu valor alterado após a criação. Mas existem alguns que podem ser
        // utilizados para obter versões modificadas destes objetos

        LocalDate d = LocalDate.of(2015, 4, 1); // 2014-04-01
        d = d.withDayOfMonth(15).withMonth(3); // chaining
        System.out.println(d); // 2015-03-15

        // Cada método with chamado retorna um novo objeto, com o valor modificado.
        // O objeto original nunca tem seu valor alterado:
        LocalDate d2 = LocalDate.of(2013, 9, 7);
        System.out.println(d2); // 2013-09-07
        // with month
        d2.withMonth(12);
        System.out.println(d2); // 2013-09-07

        //
        // Lembre se que só é possível manipular partes da data em objetos que têm
        // estas partes. O exemplo a seguir não compila pois “LocalTime does not have
        // a day of month field”.

        LocalTime d3 = LocalTime.now();
        // d3.withDayOfMonth(15); // compile error

        // Caso o objetivo seja incrementar ou decrementar alguma parte da data,
        // temos os métodos plus e minus:
        LocalDate d4 = LocalDate.of(2013, 9, 7);
        d4 = d4.plusDays(1).plusMonths(3).minusYears(2);
        System.out.println(d4); // 2011-12-08

        // Podemos adicionar os mesmos três meses usando uma ENUM de unidade
        // de tempo. Cuidado que não estamos falando para alterar o campo
        // ChronoField.WEEK. Isso seria errado, pois não queremos alterarumcampo
        // de semana, ou mesmo de dia

        LocalDate d5 = LocalDate.of(2013, 9, 7);
        d5 = d5.plusWeeks(3).minus(3, ChronoUnit.WEEKS);
        System.out.println(d5); // 2011-12-08

        // Para fixar se é ChronoField ou ChronoUnit, lembre-se que você deseja
        // saber ( get) o valor de um campo, então ChronoField, .DAY para o
        // dia específico. Caso você deseje adicionar dias, para adicionar N dias usa-se
        // ChronoUnit.DAYS (plural).

        LocalDate d6 = LocalDate.of(2013, 9, 7);
        // UnsupportedTemporalTypeException
        // LocalDate does not support hours!
        d6 = d6.plus(3, ChronoUnit.HOURS);
        System.out.println(d6);
    }

    // Convertendo entre os diversos tipos de datas
    public static void test05() {
        // A classe LocalDateTime possuimétodos para converter esta data/hora
        // em objetos que só possuem a data ( LocalDate) ou que só possuem a hora
        // ( LocalTime):

        LocalDateTime now = LocalDateTime.now();
        LocalDate dateNow = now.toLocalDate(); // from datetime to date
        LocalTime timeNow = now.toLocalTime(); // from datetime to time

        // As classes também possuem métodos para combinar suas partes e criar
        // um novo objeto modificado:

        LocalDateTime now2 = LocalDateTime.now();
        LocalDate dateNow2 = now2.toLocalDate(); // from datetime to date
        LocalTime timeNow2 = now2.toLocalTime(); // from datetime to time

        // from date to datetime
        LocalDateTime nowAtTime1 = dateNow.atTime(timeNow);
        // from time to datetime
        LocalDateTime nowAtTime2 = timeNow.atDate(dateNow);
    }

    // Trabalhando com a API legada
    public static void test06() {
        // Para tornar a API legada compatível com a API nova, foram introduzidos
        // vários métodos nas antigas classes java.util.Date e java.util.Calendar.
        // O exemplo a seguir converte uma java.util.Date em LocalDateTime usando a timezone padrão do sistema:

        Date d = new Date();

        Instant i1 = d.toInstant();

        LocalDateTime ldt1 = LocalDateTime.ofInstant(i1, ZoneId.systemDefault());

        // O próximo exemplo transforma um Calendar pelo mesmo processo:
        Calendar c = Calendar.getInstance();
        Instant i2 = c.toInstant();
        LocalDateTime ldt2 = LocalDateTime.ofInstant(i2, ZoneId.systemDefault());

        // Repare que para fazer a conversão usamos como intermediário a classe
        // Instant, que representa o número de milissegundos desde 01/01/1970.
        // Também podemos usar essa classe para fazer o caminho de volta:
        Instant i3 = d.toInstant();
        LocalDateTime ldt3 = LocalDateTime.ofInstant(i1, ZoneId.systemDefault());
        Instant instant = ldt3.toInstant(ZoneOffset.UTC);
        Date date = Date.from(instant);
    }

    // Cálculos de intervalo de tempo com datas
    public static void test07() {
        // Duration é a classe de mais baixo nível, usada para manipular objetos
        // do tipo Instant. O exemplo a seguir soma 10 segundos ao instante atual:
        Instant now = Instant.now(); // now
        Duration tenSeconds = Duration.ofSeconds(10); // 10 seconds
        Instant t = now.plus(tenSeconds); // now after 10 seconds

        // O próximo exemplo mostra como pegar o intervalo em segundos entre dois instantes:
        Instant t1 = Instant.EPOCH; // 01/01/1970 00:00:00
        Instant t2 = Instant.now();
        long secondsSinceEpoch = Duration.between(t1, t2).getSeconds();

        // Note que Duration só tem a opção de getSeconds; não existem métodos
        // do tipo getDays etc

        // ChronoUnit é uma das classes mais versáteis, pois permite ver a diferença
        // entre duas datas em várias unidades de tempo:
        LocalDate birthday = LocalDate.of(1983, 7, 22);
        LocalDate base = LocalDate.of(2014, 12, 25);

        // 31 years total
        System.out.println(ChronoUnit.YEARS.between(birthday, base));
        // 377 months total
        System.out.println(ChronoUnit.MONTHS.between(birthday, base));
        // 11479 days total
        System.out.println(ChronoUnit.DAYS.between(birthday, base));

        // Já a classe Period pode ser usada para fazer cálculos de intervalos, quebrando
        // as unidades de tempo do maior para o menor. Vamos tentar calcular a idade de uma pessoa:

        Period lifeTime = Period.between(birthday, base);

        System.out.println(lifeTime.getYears()); // 31 years
        System.out.println(lifeTime.getMonths()); // 5 months
        System.out.println(lifeTime.getDays()); // 3 days
    }

    // Formatando e convertendo em texto
    public static void test08() {

        // Para formatar a impressão de nossas datas, usamos a classe
        // DateTimeFormatter, do pacote java.time.format. Ele segue o
        // mesmo padrão da clássica SimpleDateFormat.

        LocalDate birthday = LocalDate.of(1983, 7, 22);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");

        System.out.println(formatter.format(birthday)); // 1983 07 22

        // Também podemos passar o formatador como parâmetro para o método
        // format dos objetos de data:
        System.out.println(birthday.format(formatter)); // 1983 07 22

    }

}
