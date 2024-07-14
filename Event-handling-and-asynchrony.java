Trong Spring, bạn có thể xử lý sự kiện và thực hiện các phương thức bất đồng bộ một cách hiệu quả bằng cách sử dụng EventListener và Async.

1. EventListener trong Spring
EventListener trong Spring cho phép bạn lắng nghe và xử lý các sự kiện trong ứng dụng. Các sự kiện này có thể là các sự kiện framework (ví dụ như khi context khởi động hoặc một bean được khởi tạo) hoặc các sự kiện do người dùng định nghĩa.

Ví dụ sử dụng @EventListener:
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyEventListener {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        // Xử lý khi context khởi động hoàn tất
        System.out.println("Application context refreshed: " + event.getApplicationContext().getId());
    }

    @EventListener
    public void handleCustomEvent(CustomEvent customEvent) {
        // Xử lý khi sự kiện custom được gửi
        System.out.println("Custom event received: " + customEvent.getMessage());
    }
}
Trong ví dụ trên:

@Component: Đánh dấu lớp là một Spring bean để Spring có thể quản lý và tự động quản lý các sự kiện.
@EventListener: Đánh dấu phương thức để lắng nghe một hoặc nhiều loại sự kiện.
2. Async trong Spring
Spring hỗ trợ các phương thức bất đồng bộ để xử lý các tác vụ mà không cần chờ đợi kết quả trả về trực tiếp. Điều này giúp cải thiện hiệu suất của ứng dụng bằng cách cho phép các tác vụ được thực hiện song song.

Ví dụ sử dụng @Async:

Đầu tiên, bạn cần cấu hình @EnableAsync trong configuration của ứng dụng:
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
}
Sau đó, bạn có thể sử dụng @Async trên các phương thức mà bạn muốn thực hiện bất đồng bộ:
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Async
    public void asyncMethod() {
        // Phương thức này sẽ được thực thi bất đồng bộ
        System.out.println("Async method started");
        // Thực hiện công việc bất đồng bộ ở đây
    }
}
Tóm tắt
EventListener: Cho phép bạn lắng nghe và xử lý các sự kiện trong ứng dụng Spring.
Async: Cho phép bạn thực hiện các phương thức một cách bất đồng bộ, cải thiện hiệu suất của ứng dụng bằng cách thực hiện các tác vụ song song. Bạn cần cấu hình @EnableAsync và sử dụng @Async trên các phương thức cần thực hiện bất đồng bộ.
Việc sử dụng EventListener và Async giúp cho việc quản lý sự kiện và xử lý tác vụ trong ứng dụng Spring trở nên dễ dàng và hiệu quả hơn.
