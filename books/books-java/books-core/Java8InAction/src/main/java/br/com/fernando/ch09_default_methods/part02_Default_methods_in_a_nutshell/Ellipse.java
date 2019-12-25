package br.com.fernando.ch09_default_methods.part02_Default_methods_in_a_nutshell;

import br.com.fernando.ch09_default_methods.part02_Default_methods_in_a_nutshell.Test.Resizable;

class Ellipse implements Resizable {

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
    }

    @Override
    public void draw() {
    }

}
