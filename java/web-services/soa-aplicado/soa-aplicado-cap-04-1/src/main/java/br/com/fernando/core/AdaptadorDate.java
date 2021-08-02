package br.com.fernando.core;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
