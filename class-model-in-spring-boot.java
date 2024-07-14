Trong ứng dụng Spring Boot, bạn thường sử dụng mô hình lớp theo một số nguyên tắc thiết kế phổ biến như MVC (Model-View-Controller) để phân tách logic ứng dụng. Dưới đây là mô hình lớp cơ bản trong Spring Boot:

1. Lớp Controller
Lớp Controller trong Spring Boot được sử dụng để xử lý các yêu cầu HTTP từ phía người dùng và đưa ra phản hồi tương ứng. Nó là điểm vào của ứng dụng và thường chịu trách nhiệm xử lý logic điều hướng và giao tiếp với người dùng thông qua các API hoặc giao diện người dùng.

Ví dụ: UserController.java

java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
2. Lớp Service (Business Logic)
Lớp Service trong Spring Boot chịu trách nhiệm thực hiện logic kinh doanh của ứng dụng. Nó là nơi thực hiện các phương thức xử lý nghiệp vụ, có thể gọi các phương thức từ lớp Repository để truy cập và xử lý dữ liệu từ cơ sở dữ liệu.

Ví dụ: UserService.java

java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
3. Lớp Repository (Persistence)
Lớp Repository trong Spring Boot cung cấp các phương thức để truy xuất và quản lý dữ liệu trong cơ sở dữ liệu. Nó sử dụng các Annotation như @Repository và kế thừa từ JpaRepository hoặc CrudRepository để thực hiện các thao tác CRUD (Create, Read, Update, Delete) cơ bản với cơ sở dữ liệu.

Ví dụ: UserRepository.java

java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Các phương thức CRUD sẽ được tự động tạo ra bởi Spring Data JPA
}
Tóm tắt
Controller: Xử lý các yêu cầu HTTP từ người dùng và gọi phương thức từ Service.
Service: Thực hiện logic kinh doanh của ứng dụng và gọi các phương thức từ Repository để truy cập dữ liệu.
Repository: Cung cấp các phương thức để thao tác với cơ sở dữ liệu, bao gồm CRUD operations.
Mô hình này giúp phân tách rõ ràng các lớp và trách nhiệm trong ứng dụng Spring Boot, giúp dễ dàng bảo trì và mở rộng mã nguồn
