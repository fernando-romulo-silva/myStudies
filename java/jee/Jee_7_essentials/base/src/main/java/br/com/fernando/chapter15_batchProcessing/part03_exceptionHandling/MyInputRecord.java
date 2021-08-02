package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import java.io.Serializable;

public class MyInputRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

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
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final MyInputRecord that = (MyInputRecord) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "MyInputRecord: " + id;
    }
}
