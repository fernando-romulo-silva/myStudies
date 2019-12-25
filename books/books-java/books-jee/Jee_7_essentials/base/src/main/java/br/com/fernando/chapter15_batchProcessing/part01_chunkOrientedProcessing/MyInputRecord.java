package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

// ---------------------------------------------------------------------------------------------------------------------
// MyInputRecord is an item that is read from the input source.
// A simple input record can be defined thusly:
public class MyInputRecord {

    private int id;

    public MyInputRecord() {
    }

    public MyInputRecord(int id) {
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
        return "MyInputRecord: " + id;
    }
}
