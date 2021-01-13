package consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

public class rabbitMQConTest {
    private static String host = "192.168.244.130";
    private static String userName = "guest";
    private static String passWord = "guest";
    private static int port = 5672;

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    private final static String QUEUE_NAME = "SIMPLE_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(passWord);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",false,false,null);
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("waiting for message ......");
        //对列绑定
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"wuzz.test");
        //声明消费者
        // 创建消费者

        Consumer consumer1 = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "'");
                System.out.println("consumerTag : " + consumerTag );
                System.out.println("deliveryTag : " + envelope.getDeliveryTag() );
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, true, consumer1);


        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true) {
            QueueingConsumer.Delivery deliver = consumer.nextDelivery();
            System.out.println("reciver messager:"+new String(deliver.getBody()));
            System.out.println("consumerTag : " + consumer.getConsumerTag() );
            System.out.println("deliveryTag : " + consumer.nextDelivery().getEnvelope().getDeliveryTag() );
        }


    }
}
