package br.com.fernando.myExamCloud.developWebApplicationsJSFs;

public class Question02 {

    // Given code :
    // index.xhtml
    /**
     * <pre>
     *       <h:commandLink action="examBoatSearch">                
     *       	   <h:outputText value="Proceed to ExamBoat Search Page"/>
     *       </h:commandLink>
     * </pre>
     */
    //
    // Which navigation rule correctly defines examBoatSearch?
    // (Assume that ExamBoat search page is search.jsp.)
    //
    // Choice A
    /**
     * <pre>
     *    <navigation-rule>    
     *        <from-view-id>/index.xhtml</from-view-id>    
     *        <navigation-case>    
     *            <from-action>examBoatSearch</from-action>    
     *            <to-view-id>/search.jsp</to-view-id>    
     *        </navigation-case>    
     *    </navigation-rule>
     * </pre>
     **/
    //
    // Choice B
    /**
     * <pre>
     *     <navigation-rule>                                      
     *         <from-view-id>/index.xhtml</from-view-id>            
     *         <navigation-case>                                
     *             <from-outcome>examBoatSearch</from-outcome>  
     *             <to-view-id>/search.jsp</to-view-id>         
     *         </navigation-case>                               
     *     </navigation-rule>
     * </pre>
     */
    //
    // Choice C
    /**
     * <pre>
     *      <navigation-rule>                                                  
     *          <from-view-id>/index.xhtml</from-view-id>                       
     *          <navigation-case>                                           
     *               <from-action>#{ManagedBean.actionMethod}</from-action>  
     *               <to-view-id>/search.jsp</to-view-id>}                      
     *          </navigation-case>                                             
     *      </navigation-rule>
     * </pre>
     */
    //
    // Choice D
    // None of the above, Both from-action and from-outcome are required.
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
    // Choice B is correct.
    //
    // The form-action element along with from-outcome is needed for dynamic navigation where the output of the action method
    // specified in the from-action element is compared to the from-outcome value.
    //
    // Here it is static navigation where we just need to call next page.
    // For this static navigation, this is the navigation from-outcome
    // value i.e. examBoatSearch that will map to search.xhtml in the navigation rule.
    //
    // ------------------------------------------------------------------------------------------------------------------    
    //
    // Dynamic Navigation
    /**
     * <pre>
     *       <h:commandLink action="#{ManagedBean.actionMethod}"> <!-- different here -->               
     *       	   <h:outputText value="Proceed to ExamBoat Search Page"/>
     *       </h:commandLink>
     * </pre>
     */

    /**
     * <pre>
     * 
     *      <navigation-rule>
     *           <from-view-id>/index.xhtml</from-view-id>
     *           <navigation-case>
     *               <from-action>#{ManagedBean.actionMethod}</from-action>
     *               <to-view-id>/search.jsp</to-view-id>
     *          </navigation-case>
     *      </navigation-rule>
     * 
     * </pre>
     */
    // ------------------------------------------------------------------------------------------------------------------
    //
    // Static Navigation
    /**
     * <pre>
     *       <h:commandLink action="examBoatSearch">                
     *       	   <h:outputText value="Proceed to ExamBoat Search Page"/>
     *       </h:commandLink>
     * </pre>
     */

    /**
     * <pre>
     *     <navigation-rule>                                      
     *         <from-view-id>/index.xhtml</from-view-id>            
     *         <navigation-case>                                
     *             <from-outcome>examBoatSearch</from-outcome>  
     *             <to-view-id>/search.jsp</to-view-id>         
     *         </navigation-case>                               
     *     </navigation-rule>
     * </pre>
     */

}