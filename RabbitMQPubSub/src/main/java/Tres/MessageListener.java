package Tres;

import javax.jms.Message;
import javax.jms.TextMessage;

public class MessageListener {
    static class ListenrOne implements javax.jms.MessageListener {
        @Override
        public void onMessage(Message message) {
            TextMessage textMessage = (TextMessage) message;
            try{
                System.out.println(textMessage.getText());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
