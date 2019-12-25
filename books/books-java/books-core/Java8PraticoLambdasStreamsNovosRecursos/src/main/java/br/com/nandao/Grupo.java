package br.com.nandao;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Grupo {
    private final Set<Usuario> usuarios = new HashSet<>();

    public void add(final Usuario u) {
        usuarios.add(u);
    }

    public Set<Usuario> getUsuarios() {
        return Collections.unmodifiableSet(this.usuarios);
    }
}
