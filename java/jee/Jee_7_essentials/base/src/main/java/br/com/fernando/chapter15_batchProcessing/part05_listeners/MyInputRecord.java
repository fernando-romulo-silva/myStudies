package br.com.fernando.chapter15_batchProcessing.part05_listeners;

public class MyInputRecord {
    private int id;

    public MyInputRecord() {
    }

    public MyInputRecord(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyInputRecord: " + id;
    }
}
