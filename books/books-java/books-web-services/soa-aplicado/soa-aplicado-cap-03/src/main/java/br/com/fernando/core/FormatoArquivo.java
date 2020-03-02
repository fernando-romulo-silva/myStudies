package br.com.fernando.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

// Outro recurso interessante do JAXB é a possibilidade de se trabalhar com enums.
// Podemos realizar a customização do nome do tipo utilizando a anotação 'javax.xml.bind.annotation.XmlEnum'. 
// Também podemos customizar os valores utilizando a anotação javax.xml.bind.annotation.XmlEnumValue. 

@XmlEnum
@XmlType(name = "formato")
public enum FormatoArquivo {

    @XmlEnumValue("pdf")
    PDF,

    @XmlEnumValue("mobi")
    MOBI,

    @XmlEnumValue("epub")
    EPUB;

}
