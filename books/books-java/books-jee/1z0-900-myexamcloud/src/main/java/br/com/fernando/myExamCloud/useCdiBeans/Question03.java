package br.com.fernando.myExamCloud.useCdiBeans;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

public class Question03 {

    public class Message {

    }

    // Given the code sample:
    public interface GenericMessenger {
	public void handleMessage(Message msg);
    }

    // And
    @Default
    public class MsgHandler implements GenericMessenger {
	public void handleMessage(Message msg) {
	}
    }

    // And

    @Alternative
    public class SmsHandler implements GenericMessenger {
	public void handleMessage(Message msg) {

	}
    }

    // And the next fragment from beans.xml:
    // <alternatives>
    // <class> com.myexamcloud.SmsHandler</class>
    // </alternatives>
    //
    // And this injection point:
    @Inject
    GenericMessenger messageHandler;
    //
    //
    // Which type would be injected at run time and referenced by the messageHandler variable?
    //
    // Choice A
    // SmsHandler
    //
    // Choice B
    // MsgHandler
    //
    // Choice C
    // None of the above. An exception would be thrown due to ambiguous bean references.
    //
    // Choice D
    // GenericMessenger
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
    // When you have more than one version of a bean you use for different purposes, you can choose between them during the development phase by injecting
    // one qualifier or another.
    // Instead of having to change the source code of your application, however, you can make the choice at deployment time by using alternatives
    //
    // The alternative version of the bean is used by the application when it is declared in beans.xml file:
    //
    // <alternatives>
    // <class> com.myexamcloud.SmsHandler</class>
    // </alternatives>
    //
}
