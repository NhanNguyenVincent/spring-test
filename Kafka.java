Kafka Overview
Apache Kafka là một nền tảng truyền dữ liệu phân tán, được thiết kế để xử lý dữ liệu dòng một cách nhanh chóng và có khả năng mở rộng cao.
  Kafka được sử dụng chủ yếu để xây dựng các pipeline dữ liệu thời gian thực và các hệ thống truyền thông phân tán.

Các thành phần chính của Kafka:
Producer: Gửi dữ liệu vào các topic của Kafka.
Consumer: Đọc dữ liệu từ các topic của Kafka.
Broker: Máy chủ Kafka quản lý lưu trữ và phân phối dữ liệu.
Topic: Kênh mà dữ liệu được ghi vào và đọc ra.
Partition: Mỗi topic có thể được chia thành nhiều partition để tăng khả năng mở rộng.
Tính năng chính của Kafka:
Scalability: Dễ dàng mở rộng bằng cách thêm nhiều broker vào cluster.
Fault-tolerance: Dữ liệu được sao chép trên nhiều broker để đảm bảo tính toàn vẹn.
High-throughput: Xử lý hàng triệu thông điệp mỗi giây.
Durability: Dữ liệu được lưu trữ ổn định trên đĩa cứng.
Xây dựng hệ thống Push Notification không dùng Firebase/Parse Server
Chúng ta có thể kết hợp Kafka và SSE để xây dựng một hệ thống push notification. Dưới đây là hướng dẫn cơ bản để thiết lập hệ thống này bằng Spring Boot.

Kiến trúc hệ thống:
Producer: Một service gửi notification vào Kafka topic.
Kafka Broker: Xử lý và phân phối các thông điệp.
Consumer: Một service đọc notification từ Kafka topic và đẩy đến client qua SSE.
1. Cài đặt Kafka
Tải Kafka từ trang chủ Apache Kafka.
Giải nén và khởi động Zookeeper:
  bin/zookeeper-server-start.sh config/zookeeper.properties
Khởi động Kafka server:
  bin/kafka-server-start.sh config/server.properties
2. Thêm dependency cho Kafka và SSE trong Spring Boot
<dependencies>
    <!-- Kafka -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    <!-- SSE -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
3. Cấu hình Kafka trong Spring Boot
application.properties
  spring.kafka.bootstrap-servers=localhost:9092
  spring.kafka.consumer.group-id=notification_group
4. Tạo Kafka Producer
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final String TOPIC = "notification_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
5. Tạo Kafka Consumer và SSE Controller
NotificationConsumer
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationConsumer {

    private final List<SseEmitter> emitters = new ArrayList<>();

    public SseEmitter addEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        this.emitters.add(emitter);
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        return emitter;
    }

    @KafkaListener(topics = "notification_topic", groupId = "notification_group")
    public void consume(String message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });
        this.emitters.removeAll(deadEmitters);
    }
}
NotificationController
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotificationController {

    @Autowired
    private NotificationProducer producer;

    @Autowired
    private NotificationConsumer consumer;

    @GetMapping("/notify")
    public String sendNotification(@RequestParam String message) {
        producer.sendNotification(message);
        return "Notification sent: " + message;
    }

    @GetMapping("/stream")
    public SseEmitter streamNotifications() {
        return consumer.addEmitter();
    }
}
6. Kiểm tra hệ thống
Gửi notification
Gửi yêu cầu HTTP GET đến /notify với tham số message.
  curl http://localhost:8080/notify?message=HelloWorld
Nhận notification qua SSE
Truy cập trang /stream để nhận thông báo.
<!DOCTYPE html>
<html>
<body>
    <h1>Push Notifications</h1>
    <div id="notifications"></div>

    <script>
        const eventSource = new EventSource('/stream');
        eventSource.onmessage = function(event) {
            const newElement = document.createElement("div");
            newElement.innerText = event.data;
            document.getElementById("notifications").appendChild(newElement);
        };
    </script>
</body>
</html>
Tổng kết
Bằng cách kết hợp Kafka và SSE, chúng ta có thể xây dựng một hệ thống push notification hiệu quả mà không cần sử dụng các dịch vụ bên thứ ba như Firebase hay Parse Server.
      Kafka đảm bảo tính phân tán và khả năng mở rộng, trong khi SSE cung cấp giải pháp đơn giản để đẩy thông báo từ server đến client theo thời gian thực.
