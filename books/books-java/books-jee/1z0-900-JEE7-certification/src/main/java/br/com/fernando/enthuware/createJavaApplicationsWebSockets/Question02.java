package br.com.fernando.enthuware.createJavaApplicationsWebSockets;

public class Question02 {

    // Which statement is true about the relationship between HTTP and WebSockets?
    // Select 1 option(s):
    //
    // A - A WebSocket connection is a bi-directional HTTP session with message-handling support.
    //
    // B - A WebSocket connection is initialized with an HTTP handshake.
    //
    // C - A WebSocket connection can be initialized by either client or server.
    //
    // D - A WebSocket connection uses HTTP protocol to exchange data with the browser.
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
    // The answer B is correct
    // Any WebSocket implementation must use HTTP for the initial handshake as mandated by the WebSocket protocol specification, RFC 6455.
    //
    // A is wrong because:
    // WebSocket is a bi-directional connection but it is not an HTTP session. It is a different protocol than HTTP.
    //
    // C is wrong because:
    // It can be initialized only by the client.
    //
    // D is wrong because:
    // HTTP is used only for the handshake.
    // Once the WebSocket handshake is done, only the WebSocket protocol is used to exchange the data.

}
