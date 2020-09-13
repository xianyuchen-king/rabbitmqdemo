package consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class rabbitMQConTopic {
    private static String host = "192.168.244.130";
    private static String userName = "guest";
    private static String passWord = "guest";
    private static int port = 5672;
    public static void main(String[] args) {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(userName);
            factory.setPassword(passWord);
            Connection connect = factory.newConnection();
            Channel channel = connect.createChannel();
            //声明exchangeType 类型为 topic 名称为my.topc
            channel.exchangeDeclare("my.topic","topic", true);

            //声明queue
            channel.queueDeclare("my.topic.queue", false, false, false, null);

            //exchangeType 和 routingKey 绑定 （*匹配一个单词，#匹配多个单词）
            channel.queueBind("my.topic.queue", "my.topic", "*.topic");
            //获取消息
            QueueingConsumer consumer = new QueueingConsumer(channel);
            String result = channel.basicConsume("my.topic.queue", false, consumer);
            System.out.println(result);
            while (true) {
                QueueingConsumer.Delivery deliver = consumer.nextDelivery();
                System.out.println("reciver messager:"+new String(deliver.getBody()));
                channel.basicAck(deliver.getEnvelope().getDeliveryTag(), false);
            }
        } catch (Exception e) {
        }
    }
}
