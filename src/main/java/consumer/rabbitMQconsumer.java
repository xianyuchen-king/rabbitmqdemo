package consumer;

import com.rabbitmq.client.*;

public class rabbitMQconsumer {
    private static String host = "192.168.244.130";
    private static String username = "guest";
    private static String password = "guest";
    private static int port = 5672;

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            Connection connect = factory.newConnection();
            Channel channel = connect.createChannel();
            AMQP.Queue.DeclareOk declareOK = channel.queueDeclare("test", false, false, false, null);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume("test", true, consumer);
            while (true) {
                QueueingConsumer.Delivery deliver = consumer.nextDelivery();
                System.out.println("reciver messager:"+new String(deliver.getBody()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
