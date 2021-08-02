package br.com.nandao.cap10NovaApiDatas.part04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Formatando com a nova API de datas
public class Test {

    public static void main(String[] args) {
        final LocalDateTime agora1 = LocalDateTime.now();
        final String resultado1 = agora1.format(DateTimeFormatter.ISO_LOCAL_TIME);
        
        System.out.println(resultado1);
        
        // Note que usamos um DateTimeFormatter predefinido, o
        // ISO_LOCAL_TIME. Assim como ele existem diversos outros que você pode
        // ver no javadoc do DateTimeFormatter.
        // Mas como criar um DateTimeFormatter com um novo padrão? Uma das
        // formas é usando o método ofPattern, que recebe uma String como parâmetro:
        
        final LocalDateTime agora2 = LocalDateTime.now();
        final String resultado2 = agora2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        System.out.println(resultado2);
        
        // De forma parecida, podemos transformar uma String em alguma representação
        // de data ou tempo válida, e para isso utilizamos o método parse! Podemos,
        // por exemplo, retornar o nosso resultado anterior em um LocalDate utilizando o
        // mesmo formatador:
        
        final LocalDateTime agora3 = LocalDateTime.now();
        final DateTimeFormatter formatador1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final String resultado = agora3.format(formatador1);
        final LocalDate agoraEmData = LocalDate.parse(resultado, formatador1);
        
        System.out.println(agoraEmData+" "+resultado);
    }

}
