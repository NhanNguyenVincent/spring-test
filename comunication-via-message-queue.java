Sử dụng Message Queue để giao tiếp giữa các dịch vụ trong kiến trúc microservices là một cách hiệu quả để đảm bảo tính bất đồng bộ và giảm sự phụ thuộc giữa các dịch vụ.
  Đây là một hướng dẫn cơ bản về cách giao tiếp giữa các dịch vụ thông qua Message Queue trong Spring Boot với RabbitMQ.

1. Tạo project Spring Boot cho các dịch vụ
Giả sử bạn có hai dịch vụ: Service A và Service B.

2. Cài đặt RabbitMQ
Đảm bảo RabbitMQ server đã được cài đặt và chạy trên hệ thống của bạn.

3. Thêm dependency cho RabbitMQ trong mỗi project
Trong pom.xml của mỗi dịch vụ, thêm dependency cho Spring Boot RabbitMQ:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
4. Cấu hình RabbitMQ trong mỗi dịch vụ
application.properties (hoặc application.yml) cho mỗi dịch vụ
  spring.rabbitmq.host=localhost
  spring.rabbitmq.port=5672
  spring.rabbitmq.username=guest
  spring.rabbitmq.password=guest
5. Cấu hình RabbitMQ trong Service A và Service B
RabbitMQConfig cho Service A
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigA {

    @Bean
    public Queue queueA() {
        return new Queue("serviceA.queue", true);
    }

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange("serviceA.exchange");
    }

    @Bean
    public Binding bindingA(Queue queueA, DirectExchange exchangeA) {
        return BindingBuilder.bind(queueA).to(exchangeA).with("serviceA.routingkey");
    }
}
RabbitMQConfig cho Service B
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigB {

    @Bean
    public Queue queueB() {
        return new Queue("serviceB.queue", true);
    }

    @Bean
    public DirectExchange exchangeB() {
        return new DirectExchange("serviceB.exchange");
    }

    @Bean
    public Binding bindingB(Queue queueB, DirectExchange exchangeB) {
        return BindingBuilder.bind(queueB).to(exchangeB).with("serviceB.routingkey");
    }
}
6. Tạo Producer và Consumer trong Service A và Service B
RabbitMQProducer trong Service A
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerA {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessageToServiceB(String message) {
        rabbitTemplate.convertAndSend("serviceB.exchange", "serviceB.routingkey", message);
    }
}
RabbitMQConsumer trong Service B
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumerB {

    @RabbitListener(queues = "serviceB.queue")
    public void receiveMessageFromServiceA(String message) {
        System.out.println("Received message from Service A: " + message);
        // Process the message
    }
}
7. Gửi và nhận message giữa các dịch vụ
Bạn có thể viết một controller trong Service A để gửi message đến Service B.

RabbitMQController trong Service A
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQControllerA {

    @Autowired
    private RabbitMQProducerA rabbitMQProducerA;

    @GetMapping("/sendToServiceB")
    public String sendMessage(@RequestParam String message) {
        rabbitMQProducerA.sendMessageToServiceB(message);
        return "Message sent to Service B: " + message;
    }
}
Tổng kết
Sử dụng Message Queue để giao tiếp giữa các dịch vụ giúp giảm tải và tăng độ bền vững của hệ thống.
  RabbitMQ là một giải pháp phổ biến và mạnh mẽ cho việc xử lý các tác vụ giao tiếp bất đồng bộ trong kiến trúc microservices.
  Bằng cách cấu hình RabbitMQ trong từng dịch vụ, bạn có thể dễ dàng thiết lập một hệ thống giao tiếp hiệu quả giữa các dịch vụ của mình.
