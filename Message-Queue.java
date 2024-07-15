Message Queue (MQ) là một thành phần quan trọng trong các hệ thống phân tán và microservices, giúp các dịch vụ giao tiếp với nhau một cách bất đồng bộ. Trong Java Spring, có nhiều giải pháp phổ biến để triển khai hệ thống MQ, chẳng hạn như RabbitMQ, Apache Kafka, ActiveMQ, v.v. Dưới đây là một hướng dẫn cơ bản về cách sử dụng RabbitMQ với Spring Boot.

1. Cài đặt RabbitMQ
Trước hết, bạn cần cài đặt RabbitMQ server. Bạn có thể tải xuống và cài đặt RabbitMQ từ trang chủ RabbitMQ.

2. Thêm dependency cho Spring Boot
Bạn cần thêm dependency cho Spring Boot RabbitMQ vào file pom.xml của bạn:
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
3. Cấu hình RabbitMQ
Bạn cần cấu hình kết nối với RabbitMQ trong file application.properties hoặc application.yml:

application.properties
  spring.rabbitmq.host=localhost
  spring.rabbitmq.port=5672
  spring.rabbitmq.username=guest
  spring.rabbitmq.password=guest
application.yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
4. Tạo cấu hình RabbitMQ
Tạo một lớp cấu hình RabbitMQ để khai báo các queue, exchange và binding:
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("example.queue", true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("example.exchange");
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("example.routingkey");
    }
}
5. Tạo Producer
Tạo một lớp Producer để gửi message tới RabbitMQ:
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend("example.exchange", "example.routingkey", message);
    }
}
6. Tạo Consumer
Tạo một lớp Consumer để nhận message từ RabbitMQ:
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "example.queue")
    public void receive(String message) {
        System.out.println("Received message: " + message);
    }
}
7. Kiểm tra hệ thống
Bạn có thể viết một controller hoặc một phương thức nào đó để gọi hàm send của RabbitMQProducer và kiểm tra xem RabbitMQConsumer có nhận được message hay không.

Example Controller
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        rabbitMQProducer.send(message);
        return "Message sent to RabbitMQ: " + message;
    }
}
Tổng kết
Đây là một hướng dẫn cơ bản để bắt đầu với RabbitMQ trong Spring Boot. Bạn có thể mở rộng và tùy chỉnh hệ thống của mình dựa trên các yêu cầu cụ thể.
  RabbitMQ cung cấp rất nhiều tính năng mạnh mẽ như routing, topic exchange, và nhiều hơn nữa, mà bạn có thể khám phá thêm khi xây dựng hệ thống của mình.
