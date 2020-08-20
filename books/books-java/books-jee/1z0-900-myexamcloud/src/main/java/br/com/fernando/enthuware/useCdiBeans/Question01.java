package br.com.fernando.enthuware.useCdiBeans;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

public class Question01 {

    public class Message {

    }

    public interface GenericMessenger {
	public void handleMessage(Message msg);
    }

    // And:

    @Default
    public class MsgHandler implements GenericMessenger {
	public void handleMessage(Message msg) {
	}
    }

    // And:
    @Alternative
    public class SmsHandler implements GenericMessenger {
	public void handleMessage(Message msg) {
	}
    }
    //
    // And the next fragment from beans.xml:
    // <alternatives>
    // <class>com.acme.SmsHandler</ class>
    // </alternatives>
    //
    // And this injection point:
    //
    // @Inject GenericMessenger messageHandler;
    //
    // Which type would be injected at run time and referenced by the messageHandler variable?
    //
    // A - SmsHandler
    // B - MsgHandler
    // C - None of the above. An exception would be thrown due to ambiguous bean references.
    // D - GenericMessenger
    //
    //
    // Answer A is correct 
    // Since the bean annotated with @Alternative is specified in <alternatives> element of beans.xml, it will be used.

   
}
