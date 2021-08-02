package br.com.fernando.ch09_default_methods.part01_Evolving_APIs;

import br.com.fernando.ch09_default_methods.part01_Evolving_APIs.Test.Resizable;

class Square implements Resizable {

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
