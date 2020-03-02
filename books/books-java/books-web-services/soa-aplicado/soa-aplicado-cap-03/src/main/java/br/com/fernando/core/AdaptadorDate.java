package br.com.fernando.core;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

// Utilizando Adaptadores
//
// Adaptador eh uma classe java especializada em fazer traducao de formatos, transformar um classe java em outra.
// Aqui vamos adaptar classes 'Date' para 'XMLGregorianCalendar' para fazer uma melhor conversao Java para XML
// atraves da epecificiaca JAXB. Para utilizarmos basta apenas anotar, veja :
// 
// @XmlJavaTypeAdapter(AdaptadorDate.class)
// Date dataDeCriacao = new Date();
//
// Tb eh possivel utilizar adaptadores atraves do pacote interio, utilizando combinacao de descricao do pacote e o
// XMLAdapter. Basta utilizar um package-info.java a anotar com a anotacao XmlJavaTypAdapter. Veja o arquivo 'package-info'.
public class AdaptadorDate extends XmlAdapter<XMLGregorianCalendar, Date> {

    @Override
    public XMLGregorianCalendar marshal(final Date date) throws Exception {

        final GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        final XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
        xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

        return xmlGregorianCalendar;
    }

    @Override
    public Date unmarshal(final XMLGregorianCalendar v) throws Exception {
        final Date date = v.toGregorianCalendar().getTime();
        return date;
    }
}
