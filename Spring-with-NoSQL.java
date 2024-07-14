Để giao tiếp với cơ sở dữ liệu NoSQL trong Spring, chúng ta thường sử dụng các template cụ thể được cung cấp bởi Spring Data cho từng loại cơ sở dữ liệu NoSQL như MongoDB, Redis, Cassandra, v.v. Ngoài ra, bạn cũng có thể tạo và sử dụng các document (tài liệu) tùy chỉnh nếu cần thiết. Dưới đây là ví dụ về cách làm điều đó:

1. Sử dụng Template cho giao tiếp với NoSQL
a. Dependency
Đầu tiên, bạn cần thêm dependency cho NoSQL database mà bạn muốn sử dụng vào file pom.xml (đối với Maven) hoặc build.gradle (đối với Gradle). Dưới đây là ví dụ sử dụng MongoDB:

Đối với Maven (pom.xml):

xml
<dependencies>
    <!-- Spring Boot Starter Data MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <!-- MongoDB Java Driver -->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.4.3</version>
    </dependency>
</dependencies>
b. Cấu hình
Thêm cấu hình cho kết nối MongoDB trong file application.properties (hoặc application.yml):

Đối với application.properties:

properties
# MongoDB connection settings
  spring.data.mongodb.host=localhost
  spring.data.mongodb.port=27017
  spring.data.mongodb.database=mydatabase
c. Định nghĩa Entity và Repository
Định nghĩa một document class (tài liệu) và sử dụng MongoRepository để thực hiện các thao tác CRUD:

Document (Entity) class:

java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;

    // constructors, getters/setters, other fields
}
MongoRepository:

java
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Các phương thức CRUD sẽ được tự động tạo ra bởi Spring Data MongoDB
}
d. Sử dụng Template để tạo Custom Document
Ngoài việc sử dụng MongoRepository, bạn cũng có thể sử dụng MongoTemplate để thực hiện các truy vấn tùy chỉnh hoặc phức tạp hơn. Ví dụ, bạn có thể sử dụng MongoTemplate để thêm các phương thức custom cho việc truy vấn MongoDB:

java
Copy code
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomUserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User findUserByUsername(String username) {
        return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), User.class);
    }

    // Các phương thức khác cho các truy vấn tùy chỉnh
}
Tóm tắt
Spring Data: Cung cấp các template và repository cho việc giao tiếp với các cơ sở dữ liệu NoSQL như MongoDB, Redis, Cassandra, ...
MongoDB: Ví dụ về sử dụng MongoTemplate và MongoRepository để tương tác với MongoDB trong ứng dụng Spring.
Custom Document: Bạn có thể tạo và sử dụng các document (tài liệu) tùy chỉnh nếu bạn cần thực hiện các thao tác không chuẩn hoặc phức tạp hơn.
Việc sử dụng Spring Data và các template cung cấp một cách tiếp cận linh hoạt và hiệu quả để phát triển ứng dụng giao tiếp với cơ sở dữ liệu NoSQL.
