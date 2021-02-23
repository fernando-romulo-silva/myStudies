package com.apress.prospring5.ch12;

import java.io.Serializable;
import java.util.List;

import com.apress.prospring5.ch12.entities.Singer;

public class Singers implements Serializable {
    private List<Singer> singers;

    public Singers() {
    }

    public Singers(List<Singer> singers) {
	this.singers = singers;
    }

    public List<Singer> getSingers() {
	return singers;
    }

    public void setSingers(List<Singer> singers) {
	this.singers = singers;
    }
}
