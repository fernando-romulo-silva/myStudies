package br.com.fernando.chapter15_batchProcessing.part01_chunkOrientedProcessing;

// MyOutputRecord is an item that is generated after the item is processed.
// The MyInputRecord and MyOutpuRecord classes look very similar in this case, but they might be very different in the real world.
// For example, an input record might be reading account information and an output record might be an email statement.
// A simple output record can be defined as follows:
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
