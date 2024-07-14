Trong mô hình Microservices, Eureka Server và Service Discovery (sử dụng Eureka Client) là một trong những cách phổ biến để quản lý và giám sát các dịch vụ trong hệ thống phân tán. Dưới đây là cách triển khai giao tiếp giữa các service qua Eureka:

1. Eureka Server
Eureka Server là một dịch vụ đăng ký và khám phá (service registry) trong Spring Cloud, nó cho phép các microservices đăng ký và tìm kiếm các dịch vụ khác trong mạng lưới. Bạn có thể triển khai một Eureka Server như sau:

EurekaServerApplication.java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
Trong đoạn code trên:

@SpringBootApplication: Đánh dấu đây là một ứng dụng Spring Boot.
@EnableEurekaServer: Đánh dấu ứng dụng là một Eureka Server.
application.properties (hoặc application.yml)
  spring.application.name=eureka-server
  server.port=8761
  eureka.client.register-with-eureka=false
  eureka.client.fetch-registry=false
Trong cấu hình trên, chúng ta đặt register-with-eureka=false và fetch-registry=false để chỉ định rằng Eureka Server sẽ không tự đăng ký chính nó với một Eureka Server khác và không lấy danh sách các service khác.

2. Eureka Client (Service Discovery)
Các microservices cần đăng ký với Eureka Server và sử dụng Service Discovery để giao tiếp với nhau. Dưới đây là cách triển khai một service client sử dụng Eureka Client:

ServiceApplication.java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
Trong đoạn code trên:
@SpringBootApplication: Đánh dấu đây là một ứng dụng Spring Boot.
@EnableEurekaClient: Đánh dấu ứng dụng là một Eureka Client để đăng ký với Eureka Server.
application.properties (hoặc application.yml)
  spring.application.name=my-service
  server.port=8080
  eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
Trong cấu hình trên:
  spring.application.name: Tên của service.
  server.port: Cổng mà service sẽ lắng nghe các yêu cầu.
  eureka.client.service-url.defaultZone: URL của Eureka Server để đăng ký.
Tóm tắt
Sử dụng Eureka Server và Eureka Client là một trong những cách phổ biến để quản lý và giao tiếp giữa các microservices trong mô hình Microservices của Spring Cloud.
  Eureka Server đóng vai trò là một dịch vụ đăng ký và khám phá, trong khi Eureka Client giúp các service có thể tự động đăng ký và tìm kiếm các service khác trong mạng lưới.
  Điều này giúp cho việc triển khai, mở rộng và quản lý các microservices trở nên dễ dàng và hiệu quả hơn.
