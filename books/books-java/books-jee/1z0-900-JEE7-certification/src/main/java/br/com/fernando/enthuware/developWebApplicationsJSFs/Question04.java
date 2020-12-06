package br.com.fernando.enthuware.developWebApplicationsJSFs;

public class Question04 {

    // Given the set of navigation rules:
    /**
     * <pre>
     *     <navigation-rule>
     *     	<from-view-id>list-widgets</from-view-id>
     *     	<navigation-case>
     *     		<from-outcome>add</from-outcome>
     *     		<to-view-id>add-widget</to-view-id>
     *     	</navigation-case>
     *     </navigation-rule>
     *     
     *     <navigation-rule>
     *     	<from-view-id>*</from-view-id>
     *     	<navigation-case>
     *     		<from-outcome>home</from-outcome>
     *     		<to-view-id>home</to-view-id>
     *     	</navigation-case>
     *     	<navigation-case>
     *     		<from-outcome>logout</from-outcome>
     *     		<to-view-id>goodbye</to-view-id>
     *     	</navigation-case>
     *     </navigation-rule>
     *     
     *     <navigation-rule>
     *     	<from-view-id>home</from-view-id>
     *     	<navigation-case>
     *     		<from-outcome>dashboard</from-outcome>
     *     		<to-view-id>dashboard</to-view-id>
     *     	</navigation-case>
     *     	<navigation-case>
     *     		<from-outcome>list</from-outcome>
     *     		<to-view-id>list-widgets</to-view-id>
     *     	</navigation-case>
     *     </navigation-rule>
     * 
     * </pre>
     */
    // Which of the following define a valid flow of view IDs through the application?
    // You had to select 2 option(s)
    //
    //
    // A
    // home > goodbye > list-widgets
    //
    // B
    // dashboard > home > add-widget > list-widgets
    //
    // C
    // list-widgets > add-widget > home > dashboard > home
    //
    // D
    // home > list-widgets > add-widget > goodbye
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
    // The correct answer is C and D are correct
    //
    // The item 'A' is wrong because You cannot go from goodbye to list-widgets
    // The item 'B' is wrong because Can't go from home to add-widget.

}
