package br.com.fernando.ch09_default_methods.part01_Evolving_APIs;

import java.util.List;

import br.com.fernando.ch09_default_methods.part01_Evolving_APIs.Test.Resizable;

class Utils {

    public static void paint(List<Resizable> l) {
        l.forEach(r -> {
            r.setAbsoluteSize(42, 42);
        });

        // TODO: uncomment, read the README for instructions
        // l.forEach(r -> { r.setRelativeSize(2, 2); });
    }

}
