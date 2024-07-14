Để cấu hình nhiều DataSource trong ứng dụng Spring, bạn có thể làm như sau:

1. Định nghĩa các cấu hình trong application.properties hoặc application.yml
Đầu tiên, bạn cần xác định các thông tin kết nối cho các DataSource khác nhau trong file cấu hình.

Ví dụ trong application.properties:

properties
# DataSource 1
spring.datasource.url=jdbc:mysql://localhost:3306/db1
spring.datasource.username=db1_user
spring.datasource.password=db1_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# DataSource 2
spring.datasource.db2.url=jdbc:mysql://localhost:3306/db2
spring.datasource.db2.username=db2_user
spring.datasource.db2.password=db2_password
spring.datasource.db2.driver-class-name=com.mysql.cj.jdbc.Driver
Ví dụ trong application.yml:

yaml
# DataSource 1
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db1
    username: db1_user
    password: db1_password
    driver-class-name: com.mysql.cj.jdbc.Driver

# DataSource 2
datasource:
  db2:
    url: jdbc:mysql://localhost:3306/db2
    username: db2_user
    password: db2_password
    driver-class-name: com.mysql.cj.jdbc.Driver
2. Định nghĩa các DataSource beans trong configuration class
Tiếp theo, bạn cần định nghĩa các DataSource beans trong configuration class của Spring.

Ví dụ sử dụng @Configuration và @Bean:
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource1() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/db1")
                .username("db1_user")
                .password("db1_password")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    @Qualifier("dataSource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/db2")
                .username("db2_user")
                .password("db2_password")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
3. Sử dụng @Primary và @Qualifier để chọn DataSource mặc định
Nếu bạn có nhiều DataSource và muốn chỉ định DataSource mặc định cho ứng dụng, bạn có thể sử dụng @Primary và @Qualifier.

Ví dụ sử dụng @Primary và @Qualifier:
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "dataSource1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/db1")
                .username("db1_user")
                .password("db1_password")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean(name = "dataSource2")
    @Qualifier("dataSource2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/db2")
                .username("db2_user")
                .password("db2_password")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
Tóm tắt
Cấu hình nhiều DataSource trong Spring cho phép bạn kết nối đến nhiều cơ sở dữ liệu khác nhau trong cùng một ứng dụng.
Bằng cách sử dụng các annotation như @Bean, @Configuration, @Primary, và @Qualifier, bạn có thể quản lý và sử dụng các DataSource này một cách linh hoạt và hiệu quả.
  
