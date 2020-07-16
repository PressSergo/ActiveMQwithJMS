package Tres;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Main {
    private static ActiveMQConnectionFactory activeMQConnectionFactory;
    private static TopicConnection topicConnection;
    private static TopicSession topicSession;
    private static Topic topic;
    private static TopicPublisher topicPublisher;
    private static TopicSubscriber topicSubscriber;
    private static Destination destination;

    static {
        activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.99.100:61616");
        activeMQConnectionFactory.setUserName("guest");
        activeMQConnectionFactory.setPassword("guest");
        try{
            topicConnection = activeMQConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = topicSession.createTopic("ThisFirst");
            topicConnection.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void createPublisher(){
        try{
            topicPublisher = topicSession.createPublisher(topic);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void createSubscriper(){
        try {
            topicSubscriber = topicSession.createSubscriber(new Topic() {
                @Override
                public String getTopicName() throws JMSException {
                    return "ThisFirst";
                }
            });
            topicSubscriber.setMessageListener(new MessageListener.ListenrOne());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        createPublisher();
        createSubscriper();

        try{
            for (int i = 0; ; i++){
                Thread.sleep(1000);
                topicPublisher.publish(topicSession.createTextMessage("this is a : "+i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
