package br.com.alura.ecommerce;

public class Email {

    private final String subject, log;

    public Email(String subject, String log) {
        this.subject = subject;
        this.log = log;
    }

    public String getSubject() {
        return subject;
    }

    public String getLog() {
        return log;
    }

}
