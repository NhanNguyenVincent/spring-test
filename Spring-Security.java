Spring Security là một framework mạnh mẽ và linh hoạt giúp bảo vệ các ứng dụng Spring.
  Nó cung cấp các tính năng bảo mật toàn diện, từ xác thực và ủy quyền đến bảo vệ chống lại các cuộc tấn công phổ biến như CSRF và XSS.

Các khái niệm chính trong Spring Security
Authentication (Xác thực): Quá trình xác minh danh tính của người dùng.
Authorization (Ủy quyền): Quá trình xác định quyền của người dùng sau khi đã được xác thực.
Security Context: Lưu trữ thông tin bảo mật (như đối tượng Authentication) liên quan đến phiên làm việc hiện tại của người dùng.
Filters (Bộ lọc): Spring Security sử dụng các bộ lọc để thực hiện xác thực và ủy quyền.
CSRF Protection: Bảo vệ chống lại các cuộc tấn công Cross-Site Request Forgery.
CORS Configuration: Cấu hình Cross-Origin Resource Sharing để kiểm soát truy cập tài nguyên giữa các nguồn gốc khác nhau.
Method Security: Bảo vệ các phương thức trong lớp dịch vụ hoặc lớp điều khiển thông qua các chú thích (annotations).
1. Thiết lập cơ bản
1.1. Thêm dependency
Thêm dependency Spring Security vào pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
1.2. Cấu hình bảo mật cơ bản
Tạo lớp cấu hình bảo mật bằng cách kế thừa WebSecurityConfigurerAdapter:

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll() // Cho phép truy cập công khai đến các URL bắt đầu bằng /public/
                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các URL khác
                .and()
            .formLogin() // Sử dụng form đăng nhập mặc định của Spring Security
                .permitAll()
                .and()
            .logout() // Sử dụng logout mặc định của Spring Security
                .permitAll();
    }
}
2. Authentication (Xác thực)
Spring Security hỗ trợ nhiều cơ chế xác thực khác nhau như xác thực dựa trên form, HTTP Basic, OAuth2, và LDAP.

2.1. In-Memory Authentication
Xác thực người dùng bằng cách lưu trữ thông tin người dùng trong bộ nhớ.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build());
        manager.createUser(User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .roles("ADMIN")
            .build());
        return manager;
    }
}
2.2. JDBC Authentication
Xác thực người dùng bằng cách lưu trữ thông tin người dùng trong cơ sở dữ liệu.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username = ?")
            .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");
    }
}
3. Authorization (Ủy quyền)
Spring Security cung cấp khả năng kiểm soát quyền truy cập dựa trên vai trò hoặc các biểu thức phức tạp hơn.

3.1. URL-Based Authorization
Cấu hình các quy tắc ủy quyền dựa trên URL.

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
}
3.2. Method-Based Authorization
Sử dụng các chú thích để ủy quyền truy cập cho các phương thức cụ thể.

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Secured("ROLE_ADMIN")
    public void adminMethod() {
        // Chỉ admin mới có thể truy cập
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void userMethod() {
        // Cả admin và user đều có thể truy cập
    }
}
Cần bật bảo mật dựa trên phương thức:

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MethodSecurityConfig {
    // Cấu hình bổ sung
}
4. CSRF Protection
CSRF là một loại tấn công mà kẻ tấn công buộc người dùng thực hiện các hành động không mong muốn trên một ứng dụng web nơi họ đã được xác thực.

Spring Security bảo vệ CSRF mặc định, nhưng đôi khi cần cấu hình bổ sung:

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .ignoringAntMatchers("/api/**") // Bỏ qua CSRF cho các API
                .and()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll();
    }
}
5. CORS Configuration
CORS cho phép chia sẻ tài nguyên giữa các nguồn gốc khác nhau, ngăn chặn các tấn công CSRF.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://example.com")
                        .allowedMethods("GET", POST", "PUT", "DELETE")
                        .allowedHeaders("header1", "header2")
                        .exposedHeaders("header1", "header2")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
6. OAuth2 và OpenID Connect
Spring Security hỗ trợ xác thực và ủy quyền dựa trên OAuth2 và OpenID Connect.

6.1. Cấu hình OAuth2 Login
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .oauth2Login();
    }
}
7. JWT (JSON Web Token)
JWT là một cách phổ biến để thực hiện xác thực dựa trên token trong các ứng dụng web.

7.1. Thêm dependency
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
7.2. Tạo JWT Token
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private String secretKey = "mySecretKey";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
7.3. Xác thực JWT Token
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private String secretKey = "mySecretKey";

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
8. Tích hợp với Spring Boot
Spring Boot cung cấp sự tích hợp mạnh mẽ với Spring Security thông qua các dependency và các thuộc tính cấu hình trong application.properties.

8.1. Cấu hình trong application.properties
  spring.security.user.name=user
  spring.security.user.password=password
8.2. Sử dụng các annotation tiện lợi
Spring Boot hỗ trợ các annotation như @EnableGlobalMethodSecurity để kích hoạt bảo mật dựa trên phương thức.

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    // Cấu hình bảo mật
}
Tổng kết
Spring Security là một framework mạnh mẽ và linh hoạt cho việc bảo mật các ứng dụng Spring. Nó cung cấp các tính năng toàn diện từ xác thực và ủy quyền đến bảo vệ chống lại các cuộc tấn công phổ biến.
  Với khả năng tùy chỉnh và mở rộng cao, Spring Security là một công cụ không thể thiếu cho việc bảo mật ứng dụng Java
