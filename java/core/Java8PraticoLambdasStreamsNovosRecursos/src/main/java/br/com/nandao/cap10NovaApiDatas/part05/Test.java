package br.com.nandao.cap10NovaApiDatas.part05;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

// Datas inválidas
public class Test {

    public static void main(String[] args) {

        // Ao trabalhar com Calendar, alguma vez você já pode ter se surpreendido ao executar
        // um código como este:

        final Calendar instante = Calendar.getInstance();
        instante.set(2014, Calendar.FEBRUARY, 30);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        System.out.println(dateFormat.format(instante.getTime()));

        // O resultado será: 02/03/14. Isso mesmo, o Calendar ajustou
        // o mês e dia, sem dar nenhum feedback desse erro que provavelmente passaria
        // despercebido.

        // Muito diferente desse comportamento, a nova API de datas vai lançar uma
        // DateTimeException em casos como esse. Repare:

        try {
            LocalDate.of(2014, Month.FEBRUARY, 30);
        } catch (final DateTimeException e) {
            System.out.println(e);
        }
        
        // O mesmo acontecerá se eu tentar criar um LocalDateTime com um horário inválido:

        try {
            LocalDate.now().atTime(25, 0);
        } catch (final DateTimeException e) {
            System.out.println(e);
        }
    }

}
