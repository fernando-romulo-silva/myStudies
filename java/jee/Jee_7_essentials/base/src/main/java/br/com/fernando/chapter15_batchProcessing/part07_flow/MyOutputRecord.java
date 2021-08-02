package br.com.fernando.chapter15_batchProcessing.part07_flow;

public class MyOutputRecord {

    private int id;

    public MyOutputRecord() {
    }

    public MyOutputRecord(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyOutputRecord: " + id;
    }
}
