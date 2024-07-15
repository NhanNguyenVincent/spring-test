Spring WebFlux là một phần của Spring 5, cung cấp một mô hình lập trình phản ứng (reactive programming) để xây dựng các ứng dụng web phi đồng bộ và không chặn (non-blocking).
  WebFlux dựa trên Project Reactor và hỗ trợ các API Reactive Streams.

Đặc điểm của Spring WebFlux:
Non-blocking I/O: Tất cả các hoạt động I/O đều không chặn.
Reactive Streams: Sử dụng các API Reactive Streams cho luồng dữ liệu phản ứng.
Backpressure: Quản lý lưu lượng dữ liệu giữa các thành phần để tránh quá tải.
Support for Servlet 3.1+: Có thể chạy trên các container Servlet hỗ trợ non-blocking I/O hoặc sử dụng Netty.
Các thành phần chính của WebFlux:
Mono: Đại diện cho một kết quả hoặc lỗi.
Flux: Đại diện cho một luồng nhiều kết quả hoặc lỗi.
Router Function: Xác định tuyến đường (route) và xử lý yêu cầu mà không cần các controller thông thường.
Handler Function: Xử lý các yêu cầu HTTP.
Thiết lập WebFlux trong Spring Boot
1. Thêm dependency cho WebFlux
Thêm dependency spring-boot-starter-webflux vào pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
2. Tạo Controller sử dụng WebFlux
Tạo một REST controller để xử lý các yêu cầu HTTP không đồng bộ bằng Mono và Flux.

  import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class WebFluxController {

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello, WebFlux!");
    }

    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "WebFlux", "World");
    }
}
3. Tạo Router Function và Handler Function
Sử dụng Router Function và Handler Function thay vì các annotation để định nghĩa các tuyến đường.

Handler Function
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class WebFluxHandler {

    public Mono<ServerResponse> mono(ServerRequest request) {
        return ok().body(Mono.just("Hello, WebFlux!"), String.class);
    }

    public Mono<ServerResponse> flux(ServerRequest request) {
        return ok().body(Flux.just("Hello", "WebFlux", "World"), String.class);
    }
}
Router Function
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebFluxRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(WebFluxHandler handler) {
        return route(GET("/api/mono"), handler::mono)
                .andRoute(GET("/api/flux"), handler::flux);
    }
}
WebFlux với Database Reactive
Spring Data R2DBC (Reactive Relational Database Connectivity) được sử dụng để truy cập cơ sở dữ liệu phản ứng.

1. Thêm dependency cho R2DBC và cơ sở dữ liệu
Thêm các dependency cần thiết vào pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
<dependency>
    <groupId>io.r2dbc</groupId>
    <artifactId>r2dbc-postgresql</artifactId>
    <version>0.8.8.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
2. Cấu hình cơ sở dữ liệu
application.properties
  spring.r2dbc.url=r2dbc:postgresql://localhost:5432/mydatabase
  spring.r2dbc.username=myuser
  spring.r2dbc.password=mypassword
3. Tạo entity và repository
Entity
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {

    @Id
    private Long id;
    private String name;
    private String email;

    // getters and setters
}
Repository
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
4. Sử dụng repository trong controller
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }
}
Tổng kết
Spring WebFlux cung cấp một mô hình lập trình phản ứng mạnh mẽ để xây dựng các ứng dụng web phi đồng bộ và không chặn.
Mono và Flux là các API chính của WebFlux để làm việc với luồng dữ liệu phản ứng.
WebFlux có thể được kết hợp với các công nghệ như R2DBC để xây dựng các ứng dụng full-stack phản ứng.
Bằng cách sử dụng WebFlux, bạn có thể xây dựng các ứng dụng hiệu suất cao, có khả năng mở rộng tốt hơn so với các ứng dụng dựa trên mô hình lập trình đồng bộ truyền thống
