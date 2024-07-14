Để cấu hình các profiles cho ứng dụng Spring Boot (Dev, Test, Prod), bạn có thể sử dụng các tệp cấu hình application-{profile}.properties hoặc application-{profile}.yml tương ứng với mỗi profile. Dưới đây là hướng dẫn chi tiết:

1. Sử dụng application-{profile}.properties
a. Tạo các tệp cấu hình
application-dev.properties:

properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb_dev
  spring.datasource.username=dev_user
  spring.datasource.password=dev_secret
  application-test.properties:

properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb_test
  spring.datasource.username=test_user
  spring.datasource.password=test_secret
  application-prod.properties:

properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb_prod
  spring.datasource.username=prod_user
  spring.datasource.password=prod_secret
b. Cấu hình kích hoạt profile trong application.properties
Thêm dòng sau vào application.properties để chỉ định profile mặc định (ví dụ ở đây là dev):

properties
  spring.profiles.active=dev
2. Sử dụng application-{profile}.yml
a. Tạo các tệp cấu hình
application-dev.yml:

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_dev
    username: dev_user
    password: dev_secret
application-test.yml:

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_test
    username: test_user
    password: test_secret
application-prod.yml:

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_prod
    username: prod_user
    password: prod_secret
b. Cấu hình kích hoạt profile trong application.yml
Thêm phần sau vào application.yml để chỉ định profile mặc định (ví dụ ở đây là dev):

yaml
spring:
  profiles:
    active: dev
Sử dụng các thuộc tính profile trong mã nguồn
Bạn có thể sử dụng các thuộc tính từ các tệp cấu hình profile trong mã nguồn bằng cách sử dụng annotation @Value hoặc @ConfigurationProperties, tương tự như đã mô tả trong các ví dụ trước đó.

Ví dụ tổng hợp
Dưới đây là một ví dụ tổng hợp về cách cấu hình và sử dụng profiles trong một ứng dụng Spring Boot:

Cấu hình profiles
application-dev.yml

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_dev
    username: dev_user
    password: dev_secret
application-test.yml

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_test
    username: test_user
    password: test_secret
application-prod.yml

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydb_prod
    username: prod_user
    password: prod_secret
Sử dụng trong mã nguồn
AppProperties.java
java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;

    // getters and setters
}
MyBean.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    private final DataSourceProperties dataSourceProperties;

    @Autowired
    public MyBean(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public void printDataSourceInfo() {
        System.out.println("Database URL: " + dataSourceProperties.getUrl());
        System.out.println("Database Username: " + dataSourceProperties.getUsername());
        System.out.println("Database Password: " + dataSourceProperties.getPassword());
    }
}
Application.java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
Trên đây là cách để bạn có thể cấu hình và sử dụng profiles trong ứng dụng Spring Boot một cách hiệu quả.
