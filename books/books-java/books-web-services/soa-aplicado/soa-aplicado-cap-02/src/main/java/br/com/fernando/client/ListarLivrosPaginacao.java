
package br.com.fernando.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listarLivrosPaginacao complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listarLivrosPaginacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroDaPagina" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tamanhoDaPagina" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarLivrosPaginacao", propOrder = {
    "numeroDaPagina",
    "tamanhoDaPagina"
})
public class ListarLivrosPaginacao {

    protected int numeroDaPagina;
    protected int tamanhoDaPagina;

    /**
     * Gets the value of the numeroDaPagina property.
     * 
     */
    public int getNumeroDaPagina() {
        return numeroDaPagina;
    }

    /**
     * Sets the value of the numeroDaPagina property.
     * 
     */
    public void setNumeroDaPagina(int value) {
        this.numeroDaPagina = value;
    }

    /**
     * Gets the value of the tamanhoDaPagina property.
     * 
     */
    public int getTamanhoDaPagina() {
        return tamanhoDaPagina;
    }

    /**
     * Sets the value of the tamanhoDaPagina property.
     * 
     */
    public void setTamanhoDaPagina(int value) {
        this.tamanhoDaPagina = value;
    }

}
