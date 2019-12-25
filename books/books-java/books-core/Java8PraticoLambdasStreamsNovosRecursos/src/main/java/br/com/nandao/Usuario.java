package br.com.nandao;

public class Usuario {

    private int pontos;
    private final String nome;
    private boolean moderador;

    public Usuario(final String nome) {
        this.nome = nome;
    }

    public Usuario(final String nome, final int pontos) {
        this.pontos = pontos;
        this.nome = nome;
        this.moderador = false;
    }

    public Usuario(final String nome, final int pontos, final boolean moderador) {
        this.pontos = pontos;
        this.nome = nome;
        this.moderador = moderador;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void tornaModerador() {
        this.moderador = true;
    }

    @Override
    public String toString() {
        return "Usuario " + nome;
    }

    public boolean isModerador() {
        return moderador;
    }
}
