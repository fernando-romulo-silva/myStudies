package br.com.fernando.chapter15_batchProcessing.part05_listeners;

import java.util.StringTokenizer;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

@Named
public class MyItemReader extends AbstractItemReader {

    private final StringTokenizer tokens;

    public MyItemReader() {
        tokens = new StringTokenizer("1,2,3,4,5,6,7,8,9,10", ",");
    }

    @Override
    public MyInputRecord readItem() {
        if (tokens.hasMoreTokens()) {
            return new MyInputRecord(Integer.valueOf(tokens.nextToken()));
        }
        return null;
    }
}
