package producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class rabbitMQproducer {
    private static String host = "192.168.244.130";
    private static String username = "guest";
    private static String password = "guest";
    private static int port = 5672;

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setPort(port);
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk declareOK = channel.queueDeclare("test", false, false, false, null);
            channel.basicPublish("", "test", null, "hello rabbit".getBytes("UTF-8"));
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
