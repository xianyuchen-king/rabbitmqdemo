package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class rabbitMQProTest {
    private static String host = "192.168.244.130";
    private static String userName = "guest";
    private static String passWord = "guest";
    private static int port = 5672;

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(passWord);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String msg = "乌鸦坐飞机";

        channel.basicPublish(EXCHANGE_NAME,"wuzz.test",null,msg.getBytes());
        channel.close();
        connection.close();

    }
}
