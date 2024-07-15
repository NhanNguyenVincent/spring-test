1. Filter và FilterChain
Filter
Trong Java Servlet API, một Filter là một đối tượng có thể thực hiện lọc các yêu cầu đến một tài nguyên web (như một Servlet hoặc một trang JSP) và các phản hồi từ tài nguyên đó. Các Filter thường được sử dụng để thực hiện các tác vụ như xác thực, ghi log, hoặc thay đổi các yêu cầu và phản hồi HTTP.

Tạo một Filter
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Thực hiện lọc trước khi yêu cầu được xử lý
        System.out.println("Request received at " + System.currentTimeMillis());

        // Tiếp tục chuỗi lọc
        chain.doFilter(request, response);

        // Thực hiện lọc sau khi phản hồi được tạo
        System.out.println("Response sent at " + System.currentTimeMillis());
    }

    @Override
    public void destroy() {
        // Dọn dẹp filter
    }
}
FilterChain
FilterChain là một đối tượng được truyền vào phương thức doFilter của một Filter, đại diện cho chuỗi các Filter được áp dụng cho một yêu cầu cụ thể. Gọi chain.doFilter(request, response) sẽ chuyển yêu cầu đến Filter tiếp theo trong chuỗi hoặc đến tài nguyên đích nếu không còn Filter nào.

2. CORS (Cross-Origin Resource Sharing)
CORS là một cơ chế bảo mật web cho phép các tài nguyên từ một nguồn gốc khác có thể được yêu cầu bởi một trang web từ một nguồn gốc khác. Đây là một chính sách bảo mật quan trọng để ngăn chặn các cuộc tấn công Cross-Site Request Forgery (CSRF).

Cấu hình CORS trong Spring Boot
Sử dụng @CrossOrigin
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @CrossOrigin(origins = "http://example.com")
    @GetMapping("/data")
    public String getData() {
        return "This is CORS-enabled data";
    }
}
Cấu hình CORS bằng cách ghi đè WebMvcConfigurer
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
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("header1", "header2")
                        .exposedHeaders("header1", "header2")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
3. Request và Response
HttpServletRequest
HttpServletRequest là một interface trong Java Servlet API đại diện cho một yêu cầu HTTP từ client đến server.

Lấy thông tin từ HttpServletRequest
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {

    @GetMapping
    public String handleRequest(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        return "Client IP: " + clientIp + ", User-Agent: " + userAgent;
    }
}
HttpServletResponse
HttpServletResponse là một interface trong Java Servlet API đại diện cho một phản hồi HTTP từ server đến client.

Thiết lập thông tin trong HttpServletResponse
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @GetMapping
    public void handleResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Custom-Header", "CustomValue");
        try {
            response.getWriter().write("Hello, this is the response body");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
4. Session
Session là một cơ chế lưu trữ thông tin trạng thái giữa các yêu cầu HTTP từ cùng một người dùng. Java Servlet API cung cấp HttpSession để quản lý session.

Lưu trữ dữ liệu trong HttpSession
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/set")
    public String setSession(HttpSession session) {
        session.setAttribute("username", "JohnDoe");
        return "Session attribute set";
    }

    @GetMapping("/get")
    public String getSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return "Username from session: " + username;
    }
}
5. CSRF (Cross-Site Request Forgery)
CSRF là một loại tấn công buộc người dùng không nghi ngờ thực hiện các hành động mà họ không muốn trên một ứng dụng web nơi họ đã được xác thực.

Bảo vệ CSRF trong Spring Security
Spring Security cung cấp bảo vệ CSRF mặc định cho các ứng dụng web.

Cấu hình CSRF trong Spring Security
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .ignoringAntMatchers("/ignore-csrf") // Bỏ qua CSRF cho một số endpoint cụ thể
                .and()
            .authorizeRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated();
    }
}
Sử dụng CSRF Token trong form HTML
<form action="/process" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <!-- Các field khác của form -->
    <button type="submit">Submit</button>
</form>
Spring Security sẽ tự động thêm CSRF token vào các form nếu bạn sử dụng các thẻ form của Spring (Spring form tags).

Tổng kết
Filter: Được sử dụng để thực hiện các tác vụ trước hoặc sau khi xử lý một yêu cầu HTTP.
FilterChain: Đại diện cho chuỗi các Filter được áp dụng cho một yêu cầu cụ thể.
CORS: Cho phép chia sẻ tài nguyên giữa các nguồn gốc khác nhau, ngăn chặn các tấn công CSRF.
HttpServletRequest và HttpServletResponse: Được sử dụng để lấy thông tin từ yêu cầu HTTP và thiết lập thông tin cho phản hồi HTTP.
Session: Quản lý thông tin trạng thái giữa các yêu cầu HTTP từ cùng một người dùng.
CSRF: Bảo vệ ứng dụng khỏi các cuộc tấn công Cross-Site Request Forgery, được tích hợp sẵn trong Spring Security.
Các khái niệm này là nền tảng quan trọng trong việc xây dựng các ứng dụng web an toàn và hiệu suất cao.
