package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class rabbitMQProTopics {
    private static String host = "192.168.244.130";
    private static String userName = "guest";
    private static String passWord = "guest";
    private static int port = 5672;
    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(passWord);
            Connection connect = factory.newConnection();
            Channel channel = connect.createChannel();
            //创建exchangeType 类型为 topic
            channel.exchangeDeclare("my.topic","topic", true);
            channel.basicPublish("my.topic", "log.topic", null, "hellow my topic log".getBytes());
            channel.basicPublish("my.topic", "warn.topic", null, "hellow my topic warn".getBytes());
            channel.close();
            connect.close();
        } catch (Exception e) {
        }
    }
}
