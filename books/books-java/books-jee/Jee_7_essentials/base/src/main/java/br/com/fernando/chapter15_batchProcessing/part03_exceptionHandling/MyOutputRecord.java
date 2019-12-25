package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import java.io.Serializable;

public class MyOutputRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

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
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final MyOutputRecord that = (MyOutputRecord) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "MyOutputRecord: " + id;
    }
}
