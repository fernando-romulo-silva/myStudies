package br.com.fernando.chapter15_batchProcessing.part13_csvDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class MyItemProcessor implements ItemProcessor {

    private static int id = 1;

    private SimpleDateFormat format = new SimpleDateFormat("M/dd/yy");

    @Override
    public Person processItem(Object t) {
        System.out.println("processItem: " + t);

        StringTokenizer tokens = new StringTokenizer((String) t, ",");

        String name = tokens.nextToken();
        String date;

        try {
            date = tokens.nextToken();
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            return null;
        }

        return new Person(id++, name, date);
    }
}
