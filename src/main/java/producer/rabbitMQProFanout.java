package producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布订阅模式
 */
public class rabbitMQProFanout {
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
            //声明 exchange 的type 为 fanout 广播模式
            channel.exchangeDeclare("my.fanout1","fanout",true);
            for (int i = 0;i<100;i++){
                channel.basicPublish("my.fanout1","",null,"hello my fanout".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
