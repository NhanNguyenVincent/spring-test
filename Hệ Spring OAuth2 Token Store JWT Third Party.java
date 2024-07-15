Hệ thống xác thực nâng cao trong Spring sử dụng OAuth2, kết hợp Token Store, JWT và tích hợp với các nhà cung cấp bên thứ ba là một phương pháp phổ biến để quản lý xác thực và ủy quyền trong ứng dụng.
  Dưới đây là một số khái niệm và cấu hình cụ thể về việc triển khai hệ thống này.

1. OAuth2 Overview
OAuth2 là một giao thức ủy quyền phổ biến cho phép người dùng cấp quyền truy cập cho ứng dụng của bên thứ ba mà không cần chia sẻ mật khẩu của họ.
  Thay vì đó, ứng dụng yêu cầu và nhận được một token truy cập từ một Authorization Server, sau đó sử dụng token này để truy cập các tài nguyên được bảo vệ trên Resource Server.

2. Spring Security OAuth2
Spring Security cung cấp hỗ trợ mạnh mẽ cho việc triển khai OAuth2 trong ứng dụng Java Spring. Các thành phần chính bao gồm:

Authorization Server: Xác định quyền truy cập và cấp token cho người dùng.
Resource Server: Bảo vệ và cung cấp tài nguyên cho các ứng dụng khác thông qua token.
Client: Ứng dụng yêu cầu truy cập và sử dụng tài nguyên.
3. Cấu hình OAuth2 trong Spring Boot
Để triển khai OAuth2 trong Spring Boot với sự hỗ trợ của Spring Security, chúng ta cần cấu hình các phần sau:

a. Dependency
Thêm dependency spring-boot-starter-oauth2-client và spring-boot-starter-oauth2-resource-server vào pom.xml:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
b. Cấu hình Authorization Server
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore()) // Lưu trữ token
            .accessTokenConverter(accessTokenConverter()) // Chuyển đổi token thành JWT
            // Cấu hình AuthenticationManager để hỗ trợ grant types như password và refresh_token
            .authenticationManager(authenticationManager);
    }

    // Cấu hình lưu trữ token
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    // Cấu hình AccessTokenConverter để sử dụng JWT
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("12345"); // Đặt mã ký cho JWT
        return converter;
    }
}
c. Cấu hình Resource Server
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("resource_id"); // Xác định ID của Resource Server
    }
}
d. Cấu hình Client
# Cấu hình OAuth2 Client
  spring.security.oauth2.client.registration.custom-client-id.client-id=your-client-id
  spring.security.oauth2.client.registration.custom-client-id.client-secret=your-client-secret
  spring.security.oauth2.client.registration.custom-client-id.authorization-grant-type=authorization_code
  spring.security.oauth2.client.registration.custom-client-id.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}
  spring.security.oauth2.client.registration.custom-client-id.scope=openid,profile,email

# Cấu hình đăng nhập OAuth2
  spring.security.oauth2.client.provider.custom-client-id.authorization-uri=https://provider.com/oauth2/authorize
  spring.security.oauth2.client.provider.custom-client-id.token-uri=https://provider.com/oauth2/token
  spring.security.oauth2.client.provider.custom-client-id.user-info-uri=https://provider.com/oauth2/userinfo
  spring.security.oauth2.client.provider.custom-client-id.user-name-attribute=id
4. Tích hợp JWT
JWT (JSON Web Token) là một cách tiêu chuẩn và an toàn để trao đổi thông tin giữa các bên.
  Trong cấu hình OAuth2, JWT có thể được sử dụng như một cách để mã hóa thông tin người dùng trong các token truy cập.

Cấu hình JwtTokenStore để lưu trữ token dưới dạng JWT.
Cấu hình JwtAccessTokenConverter để chuyển đổi token giữa dạng JWT và các định dạng khác.
5. Tích hợp với các nhà cung cấp bên thứ ba
Spring Security OAuth2 cung cấp tích hợp với các nhà cung cấp bên thứ ba như Google, Facebook, GitHub,...
  Điều này cho phép ứng dụng của bạn sử dụng các tài nguyên từ những dịch vụ này mà không cần lưu trữ mật khẩu của người dùng.

Tổng kết
Việc triển khai hệ thống xác thực nâng cao trong Spring bao gồm sử dụng OAuth2 để quản lý quyền truy cập và bảo mật, kết hợp với JWT để mã hóa và chuyển đổi token.
  Điều này giúp đơn giản hóa việc xác thực và ủy quyền trong ứng dụng của bạn, đồng thời cung cấp tính bảo mật cao và tích hợp dễ dàng với các nhà cung cấp bên thứ ba.
