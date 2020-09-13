package consumer;

import com.rabbitmq.client.*;

public class rabbitMQConFanout {
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
            //声明exchange
            AMQP.Exchange.DeclareOk result = channel.exchangeDeclare("my.fanout1", "fanout",true);
            //声明 queue
            channel.queueDeclare("my.fanout.quene1", false, false, false, null);
            //绑定 queue 到 exchange
            channel.queueBind("my.fanout.quene1", "my.fanout1", "");
//			//获取消息
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume("my.fanout.quene1", false, consumer);
            channel.basicQos(1);
            while (true) {
                QueueingConsumer.Delivery deliver = consumer.nextDelivery();
                System.out.println("reciver messager:"+new String(deliver.getBody()));
                channel.basicAck(deliver.getEnvelope().getDeliveryTag(), true);
                Thread.sleep(500);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
