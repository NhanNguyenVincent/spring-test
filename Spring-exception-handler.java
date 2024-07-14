Để xử lý lỗi và trả về các thông báo lỗi phù hợp trong ứng dụng Spring, bạn có thể sử dụng các kỹ thuật như Exception Handler, trả về lỗi theo HttpStatus chuẩn hoặc theo Custom Status. Dưới đây là cách thực hiện từng phương pháp:

1. Exception Handler
Bạn có thể tạo các global exception handler hoặc handler cho từng loại ngoại lệ cụ thể trong Spring để xử lý và trả về thông báo lỗi cho client.

Ví dụ: Tạo một global exception handler
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
Trong ví dụ trên:

@ControllerAdvice: Đánh dấu lớp là một advice cho các controller trong ứng dụng.
@ExceptionHandler: Đánh dấu phương thức để xử lý một loại ngoại lệ cụ thể.
2. Trả về lỗi theo HttpStatus
Bạn có thể trực tiếp trả về các mã HTTP Status và thông điệp lỗi tương ứng.

Ví dụ: Trả về lỗi Not Found (404)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
}
3. Trả về lỗi theo Custom Status
Ngoài HttpStatus chuẩn, bạn cũng có thể trả về các mã lỗi tùy chỉnh và thông điệp lỗi tương ứng.

Ví dụ: Trả về lỗi Unauthorized (403) và thông báo tùy chỉnh
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/private-data")
    public ResponseEntity<String> getPrivateData() {
        boolean userAuthorized = checkIfUserIsAuthorized();
        if (!userAuthorized) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this resource");
        }
        return ResponseEntity.ok("Sensitive Data");
    }

    private boolean checkIfUserIsAuthorized() {
        // Logic to check if user is authorized
        return false;
    }
}
Tóm tắt
Spring cung cấp các cơ chế mạnh mẽ để xử lý và trả về thông báo lỗi cho client thông qua Exception Handler, trả về lỗi với HttpStatus chuẩn hoặc Custom Status.
Việc sử dụng các phương pháp này giúp bạn quản lý các tình huống ngoại lệ một cách hiệu quả và cung cấp phản hồi thích hợp cho người dùng cuối.

  
