package br.com.fernando.enthuware.useCdiBeans;

import javax.enterprise.inject.Alternative;

public class Question03 {

    // Given the following class definition with numbered lines:

    public interface ShoppingCart {
	public void checkout();
    }

    @Alternative
    public class ShoppingCartMock implements ShoppingCart {
	@Override
	public void checkout() {

	}
    }

    // How do you specify to use this mock CDI bean implementation instead of the regular implementation class?
    // You have to select 1 option
    //
    //
    // A
    // Use the alternatives element in the beans.xml file and specify the class name in the class element within
    //
    // B
    // Start up the server with the optional alternative command-line option, specifying any alternative class
    //
    // C
    // Reorder the implementation names in the beans.xml file such that the desired alternative is listed earlier.
    //
    // D
    // Delete the regular implementation class from the WAR.
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
    // Choice A is correct.
    //
    // The alternative version of the bean is used by the application only if that version is declared as follows in the beans.xml file:
    /**
     * <pre>
     * 
     *     <beans ... >
     *         <alternatives>
     *             <class>ShoppingCartMock</class>
     *         </alternatives>
     *     </beans>
     * 
     * </pre>
     */
    // The typical justification for an alternative bean is for testing purposes where the alternative bean presents mock data.
    //
    // The benefit it that the live system that the ‘real’ bean must connect to in order to obtain live data is remote or just too time consuming
    // to use during a test scenario. So a mock bean that provides static data is provided instead.
    //
    // To use the alternative bean you must indicate the version of the ShoppingCart bean use wish to use in the beans.xml file.
}
