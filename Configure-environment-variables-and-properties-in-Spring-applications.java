#1 -- application.properties
  application.properties là một tệp cấu hình trong Spring Boot, được sử dụng để định nghĩa các thuộc tính cấu hình cho ứng dụng. Tệp này thường nằm trong thư mục src/main/resources của dự án Spring Boot. Các thuộc tính trong tệp này có thể được sử dụng để cấu hình nhiều khía cạnh khác nhau của ứng dụng, bao gồm cấu hình cơ sở dữ liệu, cấu hình server, logging, và nhiều hơn nữa.

  Ví dụ về application.properties
  1. Cấu hình cơ sở dữ liệu
  properties
  Copy code
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb
  spring.datasource.username=root
  spring.datasource.password=secret
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  2. Cấu hình server
  properties
  Copy code
  server.port=8081
  server.servlet.context-path=/myapp
  3. Cấu hình logging
  properties
  Copy code
  logging.level.root=INFO
  logging.level.com.example.myapp=DEBUG
  logging.file.name=logs/myapp.log
  4. Cấu hình email
  properties
  Copy code
  spring.mail.host=smtp.example.com
  spring.mail.port=587
  spring.mail.username=myemail@example.com
  spring.mail.password=emailpassword
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true
  5. Cấu hình các thuộc tính tùy chỉnh
  Bạn có thể định nghĩa các thuộc tính tùy chỉnh để sử dụng trong ứng dụng của bạn.
  
  properties
  Copy code
  app.name=My Application
  app.description=This is my Spring Boot application
  app.version=1.0.0
  Sử dụng các thuộc tính từ application.properties trong mã nguồn
  Bạn có thể sử dụng các thuộc tính từ application.properties trong mã nguồn bằng cách sử dụng annotation @Value hoặc cấu hình các bean.
  
  Sử dụng @Value
  java
  Copy code
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      @Value("${app.name}")
      private String appName;
  
      @Value("${app.version}")
      private String appVersion;
  
      public void printAppInfo() {
          System.out.println("App Name: " + appName);
          System.out.println("App Version: " + appVersion);
      }
  }
  Sử dụng @ConfigurationProperties
  Một cách khác để sử dụng các thuộc tính tùy chỉnh là sử dụng @ConfigurationProperties. Cách này hữu ích khi bạn có nhiều thuộc tính liên quan với nhau.
  
  Cấu hình application.properties
  properties
  Copy code
  app.name=My Application
  app.description=This is my Spring Boot application
  app.version=1.0.0
  Tạo một lớp để ánh xạ các thuộc tính
  java
  Copy code
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  @ConfigurationProperties(prefix = "app")
  public class AppProperties {
  
      private String name;
      private String description;
      private String version;
  
      // getters and setters
  }
  Sử dụng AppProperties trong mã nguồn
  java
  Copy code
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      private final AppProperties appProperties;
  
      @Autowired
      public MyBean(AppProperties appProperties) {
          this.appProperties = appProperties;
      }
  
      public void printAppInfo() {
          System.out.println("App Name: " + appProperties.getName());
          System.out.println("App Description: " + appProperties.getDescription());
          System.out.println("App Version: " + appProperties.getVersion());
      }
  }
  Cấu hình profile-specific
  Spring Boot cho phép bạn tạo các tệp cấu hình cụ thể cho từng profile bằng cách sử dụng cú pháp application-{profile}.properties. Ví dụ:
  
  application-dev.properties
  properties
  Copy code
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb_dev
  spring.datasource.username=dev_user
  spring.datasource.password=dev_secret
  application-prod.properties
  properties
  Copy code
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb_prod
  spring.datasource.username=prod_user
  spring.datasource.password=prod_secret
  Bạn có thể kích hoạt profile cụ thể bằng cách sử dụng thuộc tính spring.profiles.active trong tệp application.properties hoặc bằng cách thiết lập biến môi trường:
  
  properties
  Copy code
  spring.profiles.active=dev
  Ví dụ tổng hợp
  Dưới đây là ví dụ tổng hợp về cách cấu hình và sử dụng application.properties trong một ứng dụng Spring Boot:
  
  application.properties
  properties
  Copy code
  spring.datasource.url=jdbc:mysql://localhost:3306/mydb
  spring.datasource.username=root
  spring.datasource.password=secret
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  
  server.port=8081
  server.servlet.context-path=/myapp
  
  logging.level.root=INFO
  logging.level.com.example.myapp=DEBUG
  logging.file.name=logs/myapp.log
  
  app.name=My Application
  app.description=This is my Spring Boot application
  app.version=1.0.0
  AppProperties.java
  java
  Copy code
  import org.springframework.boot.context.properties.ConfigurationProperties;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  @ConfigurationProperties(prefix = "app")
  public class AppProperties {
  
      private String name;
      private String description;
      private String version;
  
      // getters and setters
  }
  MyBean.java
  java
  Copy code
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      private final AppProperties appProperties;
  
      @Autowired
      public MyBean(AppProperties appProperties) {
          this.appProperties = appProperties;
      }
  
      public void printAppInfo() {
          System.out.println("App Name: " + appProperties.getName());
          System.out.println("App Description: " + appProperties.getDescription());
          System.out.println("App Version: " + appProperties.getVersion());
      }
  }
  Application.java
  java
  Copy code
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
  Trong ví dụ này, cấu hình trong application.properties được sử dụng để cấu hình cơ sở dữ liệu, server, logging, và các thuộc tính ứng dụng tùy chỉnh. Các thuộc tính ứng dụng tùy chỉnh được ánh xạ vào lớp AppProperties và được sử dụng trong lớp MyBean.


  




