package br.com.fernando.chapter15_batchProcessing.part11_partitioningJobMapper;

public class MyOutputRecord {

    private int id;

    public MyOutputRecord() {
    }

    public MyOutputRecord(final int id) {
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
        return "MyOutputRecord: " + id;
    }
}
