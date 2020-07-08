package br.com.fernando.myExamCloud.freePracticeTest.createJavaApplicationsWebSockets;

public class Question01 {

    // A developer want to create a server endpoint to wait for text messages. 
    // Which of the following code can achieve this goal?
    //
    //
    // Choice A
    /**
     * <pre>
     *  public class MyWebSocket extends Endpoint {                                                       
     *      @Override                                                                                     
     *      public void onOpen(Session session, EndpointConfig ec) {                                      
     *          final RemoteEndpoint.Basic remote = session.getBasicRemote();                             
     *          session.addMessageHandler(String.class,  new MessageHandler.Whole < String > () {         
     *              public void onMessage(String text) {                                                  
     *                  try {                                                                             
     *                      remote.sendText("Your message (" + text + ") has been recieved. Thanks !");   
     *                  } catch (IOException ioe) {                                                       
     *                      ioe.printStackTrace();                                                        
     *                  }                                                                                 
     *              }                                                                                     
     *          });                                                                                       
     *      }                                                                                             
     *  }                                                                                                 
     * </pre>
     */
    //
    // Choice B
    /**
     * <pre>
     *   @ServerEndpoint("/welcome")                                                    
     *   public class MyWebSocket {                                                     
     *       @OnMessage                                                                 
     *       public String handleMessage(String message) {                              
     *           return "Your message (" + text + ") has been recieved. Thanks !";      
     *       }                                                                          
     *   }                                                                              
     * </pre>
     */
    //
    // Choice C 	
    // Both A and B
    //
    //
    // Choice C is correct.
    //
    // The endpoint class can be implemented by API classes or by annotations. 
    // The Choice A code uses Websocket API classes and Choice B uses WebSocket API annotations.
    // 
    // The class level @ServerEndpoint annotation indicates that a Java class is to become a websocket endpoint at runtime.
    // 
    // Developers may use the value attribute to specify a URI mapping for the endpoint.
}
