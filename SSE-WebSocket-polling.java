SSE (Server-Sent Events)
Server-Sent Events (SSE) là một công nghệ cho phép server gửi các cập nhật tự động đến client qua một kết nối HTTP duy nhất.
  Đây là một giải pháp đơn giản và hiệu quả để thực hiện cập nhật thời gian thực trong các ứng dụng web.

Đặc điểm của SSE:
One-way communication: Chỉ cho phép server gửi dữ liệu đến client.
Persistent connection: Duy trì một kết nối HTTP mở để gửi dữ liệu liên tục.
Built-in reconnection: Tự động kết nối lại nếu kết nối bị gián đoạn.
Text-based: SSE sử dụng định dạng text/event-stream, thích hợp cho dữ liệu văn bản.
Sử dụng SSE trong Spring Boot:
Server Side (Spring Boot):
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class SseController {

    @GetMapping("/sse")
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().data("Hello, world!"));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }, 0, 1, TimeUnit.SECONDS);

        return emitter;
    }
}
Client Side (HTML + JavaScript):
<!DOCTYPE html>
<html>
<body>
    <h1>Server-Sent Events</h1>
    <div id="sse"></div>

    <script>
        const eventSource = new EventSource('/sse');
        eventSource.onmessage = function(event) {
            document.getElementById('sse').innerHTML += event.data + '<br>';
        };
    </script>
</body>
</html>
WebSocket
WebSocket là một giao thức truyền thông cho phép giao tiếp hai chiều (full-duplex) giữa client và server thông qua một kết nối duy nhất.

Đặc điểm của WebSocket:
Two-way communication: Cho phép cả client và server gửi dữ liệu qua lại.
Low latency: Giảm thiểu độ trễ vì không cần phải thiết lập lại kết nối mỗi lần gửi dữ liệu.
Binary and text: Hỗ trợ cả dữ liệu nhị phân và văn bản.
Sử dụng WebSocket trong Spring Boot:
Server Side (Spring Boot):
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyTextWebSocketHandler(), "/websocket").setAllowedOrigins("*");
    }
}
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyTextWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage("Hello, " + message.getPayload() + "!"));
    }
}
Client Side (HTML + JavaScript):
<!DOCTYPE html>
<html>
<body>
    <h1>WebSocket</h1>
    <input id="message" type="text">
    <button onclick="sendMessage()">Send</button>
    <div id="response"></div>

    <script>
        const socket = new WebSocket('ws://localhost:8080/websocket');

        socket.onmessage = function(event) {
            document.getElementById('response').innerHTML += event.data + '<br>';
        };

        function sendMessage() {
            const message = document.getElementById('message').value;
            socket.send(message);
        }
    </script>
</body>
</html>
Polling
Polling là một kỹ thuật trong đó client gửi các yêu cầu HTTP liên tục đến server để kiểm tra xem có dữ liệu mới hay không.

Đặc điểm của Polling:
Simple: Dễ triển khai nhưng không hiệu quả về mặt tài nguyên.
Latency: Có độ trễ cao vì phải chờ phản hồi từ server.
Resource-intensive: Tốn kém tài nguyên vì phải liên tục tạo kết nối HTTP.
Sử dụng Polling trong Spring Boot:
Server Side (Spring Boot):
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PollingController {

    @GetMapping("/poll")
    public String poll() {
        return "Hello, world!";
    }
}
Client Side (HTML + JavaScript):
<!DOCTYPE html>
<html>
<body>
    <h1>Polling</h1>
    <div id="polling"></div>

    <script>
        function poll() {
            fetch('/poll')
                .then(response => response.text())
                .then(data => {
                    document.getElementById('polling').innerHTML += data + '<br>';
                });
        }

        setInterval(poll, 1000); // Poll every second
    </script>
</body>
</html>
Tổng kết
SSE: Phù hợp cho các ứng dụng yêu cầu cập nhật từ server đến client một chiều, như thông báo thời gian thực.
WebSocket: Phù hợp cho các ứng dụng yêu cầu giao tiếp hai chiều và thời gian thực, như chat, game online.
Polling: Phù hợp cho các ứng dụng đơn giản hoặc khi không thể sử dụng SSE hay WebSocket, nhưng không hiệu quả về tài nguyên và độ trễ cao.
