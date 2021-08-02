package br.com.fernando.enthuware.developWebApplicationsJSFs;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

public class Question01 {

    // You have been asked to implement internationalization in your JSF web application.
    // Where do you configure the supported locales?
    //
    // You had to select 2 option(s)
    //
    // A
    // in the <f:view> tag of the Facelet page
    //
    // B
    // in the web.xml file
    //
    // C
    // in the faces-config.xml file
    //
    // D
    // in the src folder
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    // The correct answer is A and C
    //
    // In pages:
    // <f:view locale="language.locale">
    //
    // And
    //
    // In faces-config.xml:
    /**
     * <pre>
     *       <application>
     *          <locale-config>
     *             <default-locale>en</default-locale>
     *             <supported-locale>fr</supported-locale>
     *          </locale-config>
     *          
     *          <resource-bundle>
     *             <base-name>com.abc.Messages</base-name>
     *             <var>bundle</var>
     *          </resource-bundle>
     *       </application>
     * </pre>
     */
    // The base-name element of the resource-bundle element identifies the fully-qualified class name of the resource bundle.
    // The var element identifies the name by which the XHTML pages will reference the resource bundle.
    // For example, <h:outputText value = "#{bundle['greeting']}" />
    // The locale-config element identifies the locales supported by the resource bundle.

    @ManagedBean
    @SessionScoped
    public class LocaleBean {

	private Locale locale;

	@PostConstruct
	public void init() {
	    locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}

	public Locale getLocale() {
	    return locale;
	}

	public String getLanguage() {
	    return locale.getLanguage();
	}

	public void setLanguage(String language) {
	    locale = new Locale(language);
	    FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

    }

}
