#1 -- @SpringBootApplication
  @SpringBootApplication là một annotation quan trọng trong Spring Boot, được sử dụng để đánh dấu lớp chính của ứng dụng Spring Boot. Nó là sự kết hợp của ba annotation khác:
  @EnableAutoConfiguration: Bật tính năng tự động cấu hình của Spring Boot, nghĩa là Spring Boot sẽ cố gắng tự động cấu hình các beans dựa trên các dependencies mà bạn đã thêm vào project.
  @ComponentScan: Quét các packages để tìm các components, configurations, và services. Điều này cho phép Spring tìm thấy các lớp được chú thích với @Component, @Service, @Repository, etc., và tự động đăng ký chúng làm beans trong Spring context.
  @Configuration: Đánh dấu lớp này như một nguồn của các bean định nghĩa và là một ứng dụng Spring configuration class.
  
  Ví dụ về cách sử dụng @SpringBootApplication:
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class MySpringBootApplication {
      public static void main(String[] args) {
          SpringApplication.run(MySpringBootApplication.class, args);
      }
  }
  Khi chạy lớp này, Spring Boot sẽ khởi động ứng dụng, thực hiện quét các components và tự động cấu hình ứng dụng dựa trên các dependencies có trong classpath.

#2 -- @Component
  @Component là một annotation trong Spring Framework, được sử dụng để khai báo một lớp Java là một Spring bean. Khi một lớp được chú thích với @Component, Spring sẽ tự động phát hiện và đăng ký lớp đó làm một bean trong Spring context thông qua cơ chế quét component (component scanning).
  
  Cách sử dụng @Component
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
      public void doSomething() {
          System.out.println("Doing something...");
      }
  }
  Khác biệt giữa @Component, @Service, @Repository, và @Controller
  Mặc dù tất cả các annotation này đều là chuyên ngành của @Component và được sử dụng để đăng ký beans, chúng có mục đích cụ thể hơn:
  
  @Component: Annotation chung nhất, có thể được sử dụng cho bất kỳ lớp nào muốn trở thành một bean.
  @Service: Được sử dụng để chú thích các lớp service. Các lớp này chứa logic nghiệp vụ.
  
  import org.springframework.stereotype.Service;
  
  @Service
  public class MyService {
      public void performService() {
          // business logic
      }
  }
  @Repository: Được sử dụng để chú thích các lớp repository, thường là các lớp DAO (Data Access Object). Nó cung cấp một tầng trừu tượng để tương tác với cơ sở dữ liệu và cũng được sử dụng để quản lý ngoại lệ liên quan đến cơ sở dữ liệu.
  
  import org.springframework.stereotype.Repository;
  
  @Repository
  public class MyRepository {
      public void saveData() {
          // code to save data
      }
  }
  @Controller: Được sử dụng để chú thích các lớp controller trong ứng dụng web MVC. Các lớp này xử lý các yêu cầu HTTP và trả về phản hồi.
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.GetMapping;
  
  @Controller
  public class MyController {
      @GetMapping("/home")
      public String home() {
          return "home";
      }
  }
  Tất cả các annotation này đều giúp Spring phân loại và quản lý các bean khác nhau dựa trên vai trò và chức năng của chúng trong ứng dụng.

#3 -- @Controller, @RestController, @ResponseBody
  @Controller
  @Controller là một annotation trong Spring MVC, được sử dụng để đánh dấu một lớp như một controller xử lý các yêu cầu HTTP và trả về một tên view. Đây là thành phần cơ bản của mô hình MVC trong Spring.
  
  Ví dụ:
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.GetMapping;
  
  @Controller
  public class MyController {
  
      @GetMapping("/home")
      public String home() {
          return "home"; // trả về tên view "home"
      }
  }
  @RestController
  @RestController là một biến thể của @Controller, được sử dụng trong Spring để xây dựng các RESTful web services. Nó kết hợp @Controller và @ResponseBody, có nghĩa là tất cả các phương thức trong lớp sẽ trả về dữ liệu trực tiếp thay vì trả về một tên view.
  
  Ví dụ:
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyRestController {
  
      @GetMapping("/greet")
      public String greet() {
          return "Hello, World!"; // trả về dữ liệu trực tiếp
      }
  }
  @ResponseBody
  @ResponseBody là một annotation được sử dụng trong Spring MVC để cho biết rằng giá trị trả về của một phương thức sẽ được viết trực tiếp vào body của phản hồi HTTP. Điều này thường được sử dụng để trả về dữ liệu JSON hoặc XML.
  
  Ví dụ:
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.ResponseBody;
  
  @Controller
  public class MyControllerWithResponseBody {
  
      @GetMapping("/json")
      @ResponseBody
      public String json() {
          return "{\"message\":\"Hello, World!\"}"; // trả về dữ liệu JSON trực tiếp
      }
  }

  So sánh
  @Controller: Được sử dụng để xây dựng các controller trong ứng dụng MVC, trả về tên view.
  @RestController: Được sử dụng để xây dựng các RESTful web services, trả về dữ liệu trực tiếp (JSON, XML, v.v.).
  @ResponseBody: Được sử dụng để đánh dấu các phương thức trả về dữ liệu trực tiếp, không phải tên view.

  Ví dụ cụ thể:
  // Sử dụng @Controller
  @Controller
  public class MyController {
  
      @GetMapping("/view")
      public String view() {
          return "viewName"; // trả về tên view
      }
  }
  
  // Sử dụng @RestController
  @RestController
  public class MyRestController {
  
      @GetMapping("/data")
      public MyData data() {
          return new MyData("Hello", "World"); // trả về dữ liệu trực tiếp
      }
  }
  
  // Sử dụng @ResponseBody
  @Controller
  public class MyOtherController {
  
      @GetMapping("/string")
      @ResponseBody
      public String string() {
          return "Just a string"; // trả về dữ liệu trực tiếp
      }
  }
  Trong ví dụ trên, MyController trả về tên view, MyRestController trả về dữ liệu trực tiếp và MyOtherController cũng trả về dữ liệu trực tiếp nhưng sử dụng @ResponseBody để làm điều này.

#4 -- @Service
  @Service là một annotation trong Spring, được sử dụng để chú thích các lớp service trong ứng dụng. Nó giúp Spring framework tự động phát hiện và đăng ký các lớp này làm bean trong Spring context thông qua cơ chế quét component (component scanning). Annotation này chủ yếu được sử dụng để đánh dấu các lớp chứa logic nghiệp vụ.
  
  Cách sử dụng @Service
  import org.springframework.stereotype.Service;
  
  @Service
  public class MyService {
  
      public String performService() {
          return "Service logic executed";
      }
  }
  Tại sao sử dụng @Service?
  @Service cung cấp một cách để phân loại các thành phần của ứng dụng dựa trên chức năng của chúng. Mặc dù về mặt kỹ thuật bạn có thể sử dụng @Component để đăng ký một lớp service như một bean, @Service mang lại ý nghĩa ngữ nghĩa rõ ràng hơn, giúp mã nguồn dễ đọc và dễ bảo trì hơn. Đây là một phần của triết lý “stereotyping” của Spring, cùng với các annotation như @Repository và @Controller.
  
  Sự khác biệt giữa @Service, @Component, @Repository, và @Controller
  @Component: Annotation tổng quát, có thể được sử dụng cho bất kỳ lớp nào muốn trở thành một bean.
  @Service: Được sử dụng để chú thích các lớp chứa logic nghiệp vụ.
  @Repository: Được sử dụng để chú thích các lớp truy cập dữ liệu (DAO), cung cấp một tầng trừu tượng để tương tác với cơ sở dữ liệu và quản lý ngoại lệ liên quan đến cơ sở dữ liệu.
  @Controller: Được sử dụng để chú thích các lớp controller trong ứng dụng web MVC, xử lý các yêu cầu HTTP và trả về phản hồi.
    
  Ví dụ cụ thể:
  import org.springframework.stereotype.Service;
  
  @Service
  public class UserService {
  
      public String getUserInfo() {
          return "User info retrieved";
      }
  }
  
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;
  import org.springframework.beans.factory.annotation.Autowired;
  
  @RestController
  public class UserController {
  
      @Autowired
      private UserService userService;
  
      @GetMapping("/user")
      public String userInfo() {
          return userService.getUserInfo();
      }
  }
  Trong ví dụ trên, UserService được đánh dấu với @Service để chỉ ra rằng nó chứa logic nghiệp vụ. UserController sử dụng UserService để xử lý yêu cầu HTTP và trả về phản hồi. Spring tự động phát hiện và đăng ký UserService làm một bean trong context và sau đó tiêm nó vào UserController thông qua cơ chế Dependency Injection.

#5 -- @Repository - @ComponentScan - @Configuration
  @Repository
  @Repository là một annotation trong Spring, được sử dụng để đánh dấu các lớp truy cập dữ liệu (DAO - Data Access Object). Nó cung cấp một tầng trừu tượng để tương tác với cơ sở dữ liệu và cũng giúp quản lý ngoại lệ liên quan đến cơ sở dữ liệu.
  
  Cách sử dụng @Repository
  import org.springframework.stereotype.Repository;
  
  @Repository
  public class MyRepository {
  
      public void saveData() {
          // logic to save data
      }
  }
  @ComponentScan
  @ComponentScan là một annotation trong Spring, được sử dụng để chỉ ra các packages mà Spring nên quét để tìm các components, configurations, và services. Điều này giúp Spring tự động phát hiện và đăng ký các beans trong context.
  
  Cách sử dụng @ComponentScan
  import org.springframework.context.annotation.ComponentScan;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  @ComponentScan(basePackages = "com.example.myapp")
  public class AppConfig {
      // configuration beans
  }
  @Configuration
  @Configuration là một annotation trong Spring, được sử dụng để đánh dấu một lớp là nguồn của các bean định nghĩa và là một ứng dụng Spring configuration class. Các lớp được chú thích với @Configuration thường chứa các phương thức được chú thích với @Bean để tạo ra và cấu hình các bean.
  
  Cách sử dụng @Configuration
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      public MyService myService() {
          return new MyService();
      }
  }
  Ví dụ tổng hợp
  Kết hợp tất cả các annotation trên trong một ứng dụng Spring:
  
  // MyRepository.java
  import org.springframework.stereotype.Repository;
  
  @Repository
  public class MyRepository {
      public void saveData() {
          // logic to save data
      }
  }
  
  // MyService.java
  import org.springframework.stereotype.Service;
  import org.springframework.beans.factory.annotation.Autowired;
  
  @Service
  public class MyService {
  
      @Autowired
      private MyRepository myRepository;
  
      public void performService() {
          myRepository.saveData();
      }
  }
  
  // MyController.java
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;
  import org.springframework.beans.factory.annotation.Autowired;
  
  @RestController
  public class MyController {
  
      @Autowired
      private MyService myService;
  
      @GetMapping("/perform")
      public String perform() {
          myService.performService();
          return "Service performed!";
      }
  }
  
  // AppConfig.java
  import org.springframework.context.annotation.ComponentScan;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  @ComponentScan(basePackages = "com.example.myapp")
  public class AppConfig {
      // configuration beans
  }
  
  // Application.java
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }

  Trong ví dụ này:
  @Repository được sử dụng để đánh dấu MyRepository.
  @Service được sử dụng để đánh dấu MyService.
  @RestController được sử dụng để đánh dấu MyController.
  @ComponentScan và @Configuration được sử dụng trong AppConfig để chỉ ra rằng Spring nên quét package com.example.myapp để tìm và đăng ký các beans.
  @SpringBootApplication trong Application giúp khởi động ứng dụng Spring Boot.

#6 -- @Bean, @Conditional, @Lazy, @Primary, @Qualifier, @DependsOn
  @Bean
  @Bean là một annotation trong Spring, được sử dụng để khai báo một phương thức tạo ra một bean mà Spring sẽ quản lý. Các phương thức này thường nằm trong các lớp được chú thích với @Configuration.
  
  Cách sử dụng @Bean
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      public MyService myService() {
          return new MyService();
      }
  }
  @Conditional
  @Conditional là một annotation trong Spring, được sử dụng để điều kiện hóa việc tạo ra một bean dựa trên một số điều kiện. Điều kiện này được xác định bằng cách triển khai interface Condition.
  
  Cách sử dụng @Conditional
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Conditional;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      @Conditional(MyCondition.class)
      public MyService myService() {
          return new MyService();
      }
  }
  
  import org.springframework.context.annotation.Condition;
  import org.springframework.context.annotation.ConditionContext;
  import org.springframework.core.type.AnnotatedTypeMetadata;
  
  public class MyCondition implements Condition {
      @Override
      public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
          // logic to determine if the condition matches
          return true;
      }
  }
  @Lazy
  @Lazy là một annotation trong Spring, được sử dụng để chỉ ra rằng bean sẽ được khởi tạo khi nó thực sự cần thiết (lazy initialization) thay vì khi ứng dụng khởi động (eager initialization).
  
  Cách sử dụng @Lazy
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.Lazy;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      @Lazy
      public MyService myService() {
          return new MyService();
      }
  }
  @Primary
  @Primary là một annotation trong Spring, được sử dụng để chỉ định một bean là ưu tiên khi có nhiều bean cùng loại được tìm thấy. Điều này giúp Spring biết bean nào nên được tiêm nếu không có @Qualifier chỉ định cụ thể.
  
  Cách sử dụng @Primary
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      @Primary
      public MyService myPrimaryService() {
          return new MyService();
      }
  
      @Bean
      public MyService mySecondaryService() {
          return new MyService();
      }
  }
  @Qualifier
  @Qualifier là một annotation trong Spring, được sử dụng cùng với @Autowired để chỉ định chính xác bean nào nên được tiêm khi có nhiều bean cùng loại.
  
  Cách sử dụng @Qualifier
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private final MyService myService;
  
      @Autowired
      public MyComponent(@Qualifier("mySecondaryService") MyService myService) {
          this.myService = myService;
      }
  }
  @DependsOn
  @DependsOn là một annotation trong Spring, được sử dụng để chỉ định rằng bean này phụ thuộc vào bean khác và bean đó phải được khởi tạo trước.
  
  Cách sử dụng @DependsOn
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.DependsOn;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      public MyService myService() {
          return new MyService();
      }
  
      @Bean
      @DependsOn("myService")
      public MyOtherService myOtherService() {
          return new MyOtherService();
      }
  }
  Ví dụ tổng hợp
  // AppConfig.java
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Conditional;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.DependsOn;
  import org.springframework.context.annotation.Lazy;
  import org.springframework.context.annotation.Primary;
  
  @Configuration
  public class AppConfig {
  
      @Bean
      @Primary
      public MyService myPrimaryService() {
          return new MyService("Primary");
      }
  
      @Bean
      public MyService mySecondaryService() {
          return new MyService("Secondary");
      }
  
      @Bean
      @Lazy
      public MyLazyService myLazyService() {
          return new MyLazyService();
      }
  
      @Bean
      @DependsOn("myPrimaryService")
      public MyOtherService myOtherService() {
          return new MyOtherService();
      }
  
      @Bean
      @Conditional(MyCondition.class)
      public ConditionalService conditionalService() {
          return new ConditionalService();
      }
  }
  
  // MyComponent.java
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private final MyService myService;
  
      @Autowired
      public MyComponent(@Qualifier("mySecondaryService") MyService myService) {
          this.myService = myService;
      }
  }
  Trong ví dụ trên:
  
  @Bean được sử dụng để khai báo các bean trong AppConfig.
  @Primary chỉ định myPrimaryService là bean mặc định khi có nhiều MyService.
  @Lazy chỉ định myLazyService sẽ được khởi tạo khi cần thiết.
  @DependsOn chỉ định rằng myOtherService phụ thuộc vào myPrimaryService.
  @Conditional chỉ định rằng conditionalService sẽ được tạo ra nếu điều kiện trong MyCondition được thỏa mãn.
  @Qualifier được sử dụng trong MyComponent để chỉ định chính xác mySecondaryService sẽ được tiêm vào.

#6 -- @Autowired
  @Autowired là một annotation trong Spring, được sử dụng để tự động tiêm các bean vào các thuộc tính, phương thức setter, hoặc constructor. Spring sẽ tự động giải quyết và tiêm các bean phụ thuộc cần thiết khi ứng dụng khởi động.
  
  Cách sử dụng @Autowired
  1. Tiêm thông qua thuộc tính
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      @Autowired
      private MyService myService;
  
      public void doSomething() {
          myService.performService();
      }
  }
  2. Tiêm thông qua phương thức setter
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private MyService myService;
  
      @Autowired
      public void setMyService(MyService myService) {
          this.myService = myService;
      }
  
      public void doSomething() {
          myService.performService();
      }
  }
  3. Tiêm thông qua constructor
  Constructor-based injection là cách tiếp cận ưa thích vì nó giúp các dependencies không thể thay đổi sau khi đối tượng được khởi tạo.
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private final MyService myService;
  
      @Autowired
      public MyComponent(MyService myService) {
          this.myService = myService;
      }
  
      public void doSomething() {
          myService.performService();
      }
  }
  @Autowired với @Qualifier
  Khi có nhiều bean cùng loại trong Spring context, bạn có thể sử dụng @Qualifier cùng với @Autowired để chỉ định chính xác bean nào nên được tiêm.

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private final MyService myService;
  
      @Autowired
      public MyComponent(@Qualifier("mySecondaryService") MyService myService) {
          this.myService = myService;
      }
  
      public void doSomething() {
          myService.performService();
      }
  }
  @Autowired với Optional Dependencies
  Khi bean phụ thuộc không bắt buộc, bạn có thể sử dụng required attribute của @Autowired để chỉ định rằng dependency là optional.
  
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      @Autowired(required = false)
      private MyOptionalService myOptionalService;
  
      public void doSomething() {
          if (myOptionalService != null) {
              myOptionalService.performOptionalService();
          } else {
              // handle the case when myOptionalService is not available
          }
      }
  }
  Ví dụ tổng hợp
  Dưới đây là một ví dụ đầy đủ về cách sử dụng @Autowired trong một ứng dụng Spring:
  
  // MyService.java
  import org.springframework.stereotype.Service;
  
  @Service
  public class MyService {
      public void performService() {
          System.out.println("Service is performed");
      }
  }
  
  // MyOtherService.java
  import org.springframework.stereotype.Service;
  
  @Service
  public class MyOtherService {
      public void performOtherService() {
          System.out.println("Other Service is performed");
      }
  }
  
  // MyComponent.java
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyComponent {
  
      private final MyService myService;
      private MyOtherService myOtherService;
  
      @Autowired
      public MyComponent(MyService myService) {
          this.myService = myService;
      }
  
      @Autowired
      public void setMyOtherService(MyOtherService myOtherService) {
          this.myOtherService = myOtherService;
      }
  
      public void doSomething() {
          myService.performService();
          myOtherService.performOtherService();
      }
  }
  
  // Application.java
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.context.ApplicationContext;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          ApplicationContext context = SpringApplication.run(Application.class, args);
          MyComponent myComponent = context.getBean(MyComponent.class);
          myComponent.doSomething();
      }
  }
  Trong ví dụ này, MyComponent sử dụng cả constructor injection và setter injection để tiêm các bean MyService và MyOtherService. Khi ứng dụng chạy, Spring sẽ tự động tạo các bean và tiêm chúng vào MyComponent.

#7 -- @Value
  @Value là một annotation trong Spring, được sử dụng để tiêm các giá trị từ các thuộc tính bên ngoài (external properties) vào các biến hoặc phương thức trong các bean của Spring. Các thuộc tính này có thể đến từ các tệp cấu hình như application.properties, application.yml, các biến môi trường, hoặc các thuộc tính hệ thống.
  
  Cách sử dụng @Value
  1. Tiêm giá trị từ application.properties
  Giả sử bạn có một tệp application.properties với nội dung sau:
  
  properties
  app.name=My Application
  app.version=1.0.0
  Bạn có thể sử dụng @Value để tiêm các giá trị này vào các biến trong lớp của bạn:
  
  java
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
  2. Tiêm giá trị từ application.yml
  Giả sử bạn có một tệp application.yml với nội dung sau:
  
  yaml
  app:
    name: My Application
    version: 1.0.0
  Cách tiêm giá trị sẽ tương tự như trên:
  
  java
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
  3. Tiêm giá trị mặc định
  Bạn có thể cung cấp giá trị mặc định nếu thuộc tính không được tìm thấy:
  
  java
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      @Value("${app.name:Default Application}")
      private String appName;
  
      public void printAppName() {
          System.out.println("App Name: " + appName);
      }
  }
  4. Tiêm giá trị từ biến môi trường
  Bạn cũng có thể tiêm các biến môi trường vào các biến trong lớp của bạn:
  
  java
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      @Value("${JAVA_HOME}")
      private String javaHome;
  
      public void printJavaHome() {
          System.out.println("JAVA_HOME: " + javaHome);
      }
  }
  5. Tiêm giá trị vào phương thức
  Bạn có thể sử dụng @Value để tiêm giá trị vào các tham số của phương thức:
  
  java
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  @Component
  public class MyBean {
  
      private String appName;
  
      @Autowired
      public MyBean(@Value("${app.name}") String appName) {
          this.appName = appName;
      }
  
      public void printAppName() {
          System.out.println("App Name: " + appName);
      }
  }
  Ví dụ tổng hợp
  Dưới đây là một ví dụ đầy đủ về cách sử dụng @Value trong một ứng dụng Spring:
  
  properties
  # application.properties
  app.name=My Application
  app.version=1.0.0
    
  java
  // AppConfig.java
  import org.springframework.context.annotation.ComponentScan;
  import org.springframework.context.annotation.Configuration;
  
  @Configuration
  @ComponentScan(basePackages = "com.example.myapp")
  public class AppConfig {
      // configuration beans
  }
  
  // MyBean.java
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
  
  // Application.java
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  import org.springframework.context.ApplicationContext;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          ApplicationContext context = SpringApplication.run(Application.class, args);
          MyBean myBean = context.getBean(MyBean.class);
          myBean.printAppInfo();
      }
  }
  Trong ví dụ này, giá trị của các thuộc tính app.name và app.version từ tệp application.properties được tiêm vào các biến appName và appVersion trong MyBean, sau đó chúng được in ra trong phương thức printAppInfo.

#7 -- @Transactional, @Query, @Modifying
  @Transactional
  @Transactional là một annotation trong Spring, được sử dụng để quản lý giao dịch (transaction) trong ứng dụng. Annotation này có thể được áp dụng cho các lớp hoặc phương thức để đảm bảo rằng tất cả các thao tác dữ liệu trong phạm vi của nó được thực hiện trong cùng một giao dịch. Nếu có lỗi xảy ra trong quá trình thực hiện, toàn bộ giao dịch sẽ bị rollback.
  
  Cách sử dụng @Transactional
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import org.springframework.transaction.annotation.Transactional;
  
  @Service
  public class MyService {
  
      @Autowired
      private MyRepository myRepository;
  
      @Transactional
      public void performDatabaseOperations() {
          myRepository.save(new MyEntity());
          // Other database operations
      }
  }
  @Query
  @Query là một annotation trong Spring Data JPA, được sử dụng để định nghĩa các truy vấn JPQL hoặc SQL tuỳ chỉnh ngay trên các phương thức trong repository interface. Điều này cho phép bạn viết các truy vấn phức tạp hơn so với các phương thức truy vấn tự động được tạo bởi Spring Data JPA.
  
  Cách sử dụng @Query
  import org.springframework.data.jpa.repository.Query;
  import org.springframework.data.repository.CrudRepository;
  
  public interface MyRepository extends CrudRepository<MyEntity, Long> {
  
      @Query("SELECT m FROM MyEntity m WHERE m.name = ?1")
      MyEntity findByName(String name);
  
      @Query(value = "SELECT * FROM my_entity WHERE name = ?1", nativeQuery = true)
      MyEntity findByNameNative(String name);
  }
  @Modifying
  @Modifying là một annotation trong Spring Data JPA, thường được sử dụng cùng với @Query để chỉ định rằng truy vấn tuỳ chỉnh là một thao tác thay đổi dữ liệu như INSERT, UPDATE, hoặc DELETE. Annotation này cũng cần thiết để cho Spring biết rằng truy vấn này có thể thay đổi dữ liệu và do đó cần được thực hiện trong một giao dịch.
  
  Cách sử dụng @Modifying
  import org.springframework.data.jpa.repository.Modifying;
  import org.springframework.data.jpa.repository.Query;
  import org.springframework.data.repository.CrudRepository;
  import org.springframework.transaction.annotation.Transactional;
  
  public interface MyRepository extends CrudRepository<MyEntity, Long> {
  
      @Modifying
      @Transactional
      @Query("UPDATE MyEntity m SET m.name = ?1 WHERE m.id = ?2")
      void updateNameById(String name, Long id);
  
      @Modifying
      @Transactional
      @Query("DELETE FROM MyEntity m WHERE m.name = ?1")
      void deleteByName(String name);
  }
  Ví dụ tổng hợp
  // MyEntity.java
  import javax.persistence.Entity;
  import javax.persistence.Id;
  
  @Entity
  public class MyEntity {
      @Id
      private Long id;
      private String name;
  
      // getters and setters
  }
  
  // MyRepository.java
  import org.springframework.data.jpa.repository.Modifying;
  import org.springframework.data.jpa.repository.Query;
  import org.springframework.data.repository.CrudRepository;
  import org.springframework.transaction.annotation.Transactional;
  
  public interface MyRepository extends CrudRepository<MyEntity, Long> {
  
      @Query("SELECT m FROM MyEntity m WHERE m.name = ?1")
      MyEntity findByName(String name);
  
      @Modifying
      @Transactional
      @Query("UPDATE MyEntity m SET m.name = ?1 WHERE m.id = ?2")
      void updateNameById(String name, Long id);
  
      @Modifying
      @Transactional
      @Query("DELETE FROM MyEntity m WHERE m.name = ?1")
      void deleteByName(String name);
  }
  
  // MyService.java
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;
  import org.springframework.transaction.annotation.Transactional;
  
  @Service
  public class MyService {
  
      @Autowired
      private MyRepository myRepository;
  
      @Transactional
      public void performDatabaseOperations() {
          MyEntity entity = myRepository.findByName("Example");
          if (entity != null) {
              myRepository.updateNameById("New Name", entity.getId());
          }
          myRepository.deleteByName("Old Name");
      }
  }
  
  // Application.java
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
  Trong ví dụ này:
  
  @Transactional trong MyService đảm bảo rằng tất cả các thao tác dữ liệu trong phương thức performDatabaseOperations được thực hiện trong cùng một giao dịch.
  @Query trong MyRepository định nghĩa các truy vấn tuỳ chỉnh để tìm kiếm và cập nhật các thực thể.
  @Modifying cùng với @Transactional trong MyRepository được sử dụng để chỉ định rằng các truy vấn tuỳ chỉnh này là các thao tác thay đổi dữ liệu và cần được thực hiện trong một giao dịch.

#8 -- @RequestParam, @RequestBody, @PathVariable, @RequestHeader
  @RequestParam
  @RequestParam là một annotation trong Spring MVC, được sử dụng để ràng buộc các tham số của request (ví dụ: query parameters) vào các tham số của phương thức handler trong controller.
  
  Cách sử dụng @RequestParam
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RequestParam;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyController {
  
      @GetMapping("/greet")
      public String greet(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
          return "Hello, " + name;
      }
  }
  Trong ví dụ này, tham số name trong request URL sẽ được gán cho biến name của phương thức greet. Nếu tham số name không có trong request, giá trị mặc định sẽ là "World".
  
  @RequestBody
  @RequestBody là một annotation trong Spring MVC, được sử dụng để ràng buộc và chuyển đổi nội dung của request body thành một đối tượng Java, dựa trên các converter như Jackson (cho JSON).
  
  Cách sử dụng @RequestBody
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyController {
  
      @PostMapping("/create")
      public MyEntity create(@RequestBody MyEntity entity) {
          // process the entity
          return entity;
      }
  }
  Trong ví dụ này, nội dung của request body sẽ được chuyển đổi thành đối tượng MyEntity và được truyền vào phương thức create.
  
  @PathVariable
  @PathVariable là một annotation trong Spring MVC, được sử dụng để ràng buộc các biến đường dẫn (path variables) trong URL vào các tham số của phương thức handler trong controller.
  
  Cách sử dụng @PathVariable
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyController {
  
      @GetMapping("/user/{id}")
      public String getUser(@PathVariable("id") Long id) {
          return "User ID: " + id;
      }
  }
  Trong ví dụ này, biến đường dẫn id trong URL sẽ được gán cho biến id của phương thức getUser.
  
  @RequestHeader
  @RequestHeader là một annotation trong Spring MVC, được sử dụng để ràng buộc các giá trị của header trong request vào các tham số của phương thức handler trong controller.
  
  Cách sử dụng @RequestHeader
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RequestHeader;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  public class MyController {
  
      @GetMapping("/info")
      public String getInfo(@RequestHeader("User-Agent") String userAgent) {
          return "User-Agent: " + userAgent;
      }
  }
  Trong ví dụ này, giá trị của header User-Agent trong request sẽ được gán cho biến userAgent của phương thức getInfo.
  
  Ví dụ tổng hợp
  Dưới đây là một ví dụ đầy đủ về cách sử dụng tất cả các annotation trên trong một ứng dụng Spring MVC:
  
  java
  import org.springframework.web.bind.annotation.*;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @GetMapping("/greet")
      public String greet(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
          return "Hello, " + name;
      }
  
      @PostMapping("/create")
      public MyEntity create(@RequestBody MyEntity entity) {
          // process the entity
          return entity;
      }
  
      @GetMapping("/user/{id}")
      public String getUser(@PathVariable("id") Long id) {
          return "User ID: " + id;
      }
  
      @GetMapping("/info")
      public String getInfo(@RequestHeader("User-Agent") String userAgent) {
          return "User-Agent: " + userAgent;
      }
  }
  
  class MyEntity {
      private Long id;
      private String name;
  
      // getters and setters
  }
  Trong ví dụ này:
  
  @RequestParam được sử dụng để lấy tham số query name từ request URL.
  @RequestBody được sử dụng để chuyển đổi request body thành đối tượng MyEntity.
  @PathVariable được sử dụng để lấy biến đường dẫn id từ URL.
  @RequestHeader được sử dụng để lấy giá trị của header User-Agent từ request.

#9 -- @RequestMapping, @PostMapping, @GetMapping, @PutMapping, @DeleteMapping
  @RequestMapping
  @RequestMapping là một annotation trong Spring MVC, được sử dụng để ánh xạ các request HTTP đến các phương thức handler trong controller. Annotation này có thể được áp dụng ở cả cấp lớp và cấp phương thức, và hỗ trợ tất cả các loại request HTTP (GET, POST, PUT, DELETE, v.v.).
  
  Cách sử dụng @RequestMapping
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RequestMethod;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @RequestMapping(value = "/greet", method = RequestMethod.GET)
      public String greet() {
          return "Hello, World";
      }
  }
  @GetMapping
  @GetMapping là một biến thể của @RequestMapping, được sử dụng để ánh xạ các request HTTP GET đến các phương thức handler.
  
  Cách sử dụng @GetMapping
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @GetMapping("/greet")
      public String greet() {
          return "Hello, World";
      }
  }
  @PostMapping
  @PostMapping là một biến thể của @RequestMapping, được sử dụng để ánh xạ các request HTTP POST đến các phương thức handler.
  
  Cách sử dụng @PostMapping
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @PostMapping("/create")
      public MyEntity create(@RequestBody MyEntity entity) {
          // process the entity
          return entity;
      }
  }
  @PutMapping
  @PutMapping là một biến thể của @RequestMapping, được sử dụng để ánh xạ các request HTTP PUT đến các phương thức handler.
  
  Cách sử dụng @PutMapping
  import org.springframework.web.bind.annotation.PutMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @PutMapping("/update/{id}")
      public MyEntity update(@PathVariable Long id, @RequestBody MyEntity entity) {
          // process the update
          return entity;
      }
  }
  @DeleteMapping
  @DeleteMapping là một biến thể của @RequestMapping, được sử dụng để ánh xạ các request HTTP DELETE đến các phương thức handler.
  
  Cách sử dụng @DeleteMapping
  import org.springframework.web.bind.annotation.DeleteMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RestController;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @DeleteMapping("/delete/{id}")
      public void delete(@PathVariable Long id) {
          // process the deletion
      }
  }
  Ví dụ tổng hợp
  Dưới đây là một ví dụ đầy đủ về cách sử dụng tất cả các annotation trên trong một ứng dụng Spring MVC:
  
  java
  import org.springframework.web.bind.annotation.*;
  
  @RestController
  @RequestMapping("/api")
  public class MyController {
  
      @GetMapping("/greet")
      public String greet() {
          return "Hello, World";
      }
  
      @PostMapping("/create")
      public MyEntity create(@RequestBody MyEntity entity) {
          // process the entity
          return entity;
      }
  
      @PutMapping("/update/{id}")
      public MyEntity update(@PathVariable Long id, @RequestBody MyEntity entity) {
          // process the update
          return entity;
      }
  
      @DeleteMapping("/delete/{id}")
      public void delete(@PathVariable Long id) {
          // process the deletion
      }
  }
  
  class MyEntity {
      private Long id;
      private String name;
  
      // getters and setters
  }
  Trong ví dụ này:
  
  @RequestMapping ở cấp lớp được sử dụng để định nghĩa một URL cơ sở cho tất cả các phương thức handler trong MyController.
  @GetMapping được sử dụng để xử lý các request GET tới URL /api/greet.
  @PostMapping được sử dụng để xử lý các request POST tới URL /api/create.
  @PutMapping được sử dụng để xử lý các request PUT tới URL /api/update/{id}.
  @DeleteMapping được sử dụng để xử lý các request DELETE tới URL /api/delete/{id}.

#10 -- 

    
