Socket.IO là một thư viện giúp triển khai giao tiếp thời gian thực giữa client và server thông qua WebSockets.
  Nó cung cấp một API đơn giản và nhất quán cho việc giao tiếp hai chiều, đồng thời hỗ trợ fallback khi WebSocket không khả dụng (sử dụng polling hoặc các kỹ thuật khác).

Đặc điểm của Socket.IO:
Real-time communication: Hỗ trợ giao tiếp thời gian thực.
Bi-directional: Giao tiếp hai chiều giữa client và server.
Fallback: Tự động fallback khi WebSocket không khả dụng.
Room and Namespace: Hỗ trợ các phòng (rooms) và namespace để quản lý kết nối dễ dàng hơn.
Event-driven: Giao tiếp dựa trên sự kiện, giúp mã nguồn rõ ràng và dễ bảo trì.
Cài đặt và sử dụng Socket.IO trong Spring Boot
Để sử dụng Socket.IO trong một ứng dụng Spring Boot, bạn cần một thư viện bổ sung hỗ trợ vì Spring Boot không hỗ trợ Socket.IO trực tiếp.
  Thư viện phổ biến để tích hợp là netty-socketio.

1. Thêm dependency cho Socket.IO trong Spring Boot
Trước hết, bạn cần thêm dependency netty-socketio vào pom.xml:

<dependency>
    <groupId>com.corundumstudio.socketio</groupId>
    <artifactId>netty-socketio</artifactId>
    <version>1.7.19</version>
</dependency>
2. Cấu hình Socket.IO server
Tạo một lớp cấu hình để khởi động server Socket.IO:

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        return new SocketIOServer(config);
    }
}
3. Tạo một lớp để xử lý sự kiện
Tạo một lớp để lắng nghe và xử lý các sự kiện từ client:

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SocketIOHandler {

    private final SocketIOServer server;

    @Autowired
    public SocketIOHandler(SocketIOServer server) {
        this.server = server;
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId());
    }

    @OnEvent("message")
    public void onMessage(SocketIOClient client, String message) {
        System.out.println("Received message: " + message);
        client.sendEvent("response", "Server response: " + message);
    }
}
4. Chạy Socket.IO server cùng với Spring Boot
Bạn cần khởi động Socket.IO server khi ứng dụng Spring Boot khởi động:

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketIOServerRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public SocketIOServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
        }));
    }
}
5. Client Side (HTML + JavaScript)
Tạo một trang HTML để kết nối và giao tiếp với server Socket.IO:
<!DOCTYPE html>
<html>
<head>
    <title>Socket.IO Client</title>
    <script src="https://cdn.socket.io/4.0.0/socket.io.min.js"></script>
</head>
<body>
    <h1>Socket.IO Client</h1>
    <input id="message" type="text">
    <button onclick="sendMessage()">Send</button>
    <div id="response"></div>

    <script>
        const socket = io('http://localhost:9092');

        socket.on('connect', function() {
            console.log('Connected to server');
        });

        socket.on('disconnect', function() {
            console.log('Disconnected from server');
        });

        socket.on('response', function(data) {
            const newElement = document.createElement("div");
            newElement.innerText = data;
            document.getElementById("response").appendChild(newElement);
        });

        function sendMessage() {
            const message = document.getElementById('message').value;
            socket.emit('message', message);
        }
    </script>
</body>
</html>
Tổng kết
Socket.IO là một công cụ mạnh mẽ để xây dựng các ứng dụng thời gian thực với giao tiếp hai chiều giữa client và server.
Nó hỗ trợ WebSockets và có thể fallback sang các phương pháp khác nếu cần.
Kết hợp với Spring Boot và netty-socketio, bạn có thể dễ dàng thiết lập một hệ thống giao tiếp thời gian thực mà không cần sử dụng dịch vụ bên thứ ba.
