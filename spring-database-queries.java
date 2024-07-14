Spring cung cấp nhiều cách tiếp cận nâng cao để xử lý giao tiếp với cơ sở dữ liệu. Dưới đây là một số kỹ thuật phổ biến bạn có thể sử dụng trong ứng dụng Spring khi làm việc với cơ sở dữ liệu:

1. Criteria và Query Builder
Criteria API trong Spring Data JPA cho phép bạn xây dựng các truy vấn động một cách programmatic, linh hoạt hơn so với việc sử dụng các phương thức tĩnh (static methods) của Repository.

Ví dụ sử dụng Criteria:
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private EntityManager entityManager;

    public List<User> findUsersByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("username"), username));

        return entityManager.createQuery(cq).getResultList();
    }
}
2. Phân trang
Khi bạn cần lấy một lượng lớn dữ liệu từ cơ sở dữ liệu và chia nhỏ thành các trang, Spring Data hỗ trợ phân trang bằng cách sử dụng các phương thức phân trang có sẵn trong PagingAndSortingRepository.

Ví dụ:
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> findByUsername(String username, Pageable pageable);
}
3. Sắp xếp nhiều điều kiện
Để sắp xếp theo nhiều điều kiện trong Spring Data, bạn có thể sử dụng Sort object hoặc truy vấn JPQL có sử dụng order by.

Ví dụ sử dụng Sort:
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByStatusAndAge(String status, int age, Sort sort);
}
4. Specification
Specification là một cách để xây dựng các tiêu chí tìm kiếm động cho các truy vấn. Nó cho phép bạn tạo ra các mẫu tìm kiếm tái sử dụng được.

Ví dụ:

java
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> isUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("username"), username);
    }

    public static Specification<User> isAgeGreaterThan(int age) {
        return (root, query, cb) -> cb.greaterThan(root.get("age"), age);
    }
}
Sử dụng Specification trong Repository:
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
5. Native Query và Sử dụng Template
Khi cần thực hiện các truy vấn phức tạp hoặc tùy chỉnh, bạn có thể sử dụng Native Query trong Spring Data JPA hoặc sử dụng JdbcTemplate để thực hiện truy vấn SQL trực tiếp.

Ví dụ sử dụng Native Query:

java
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findByUsernameNativeQuery(@Param("username") String username);
}
Với JdbcTemplate, bạn có thể thực hiện truy vấn SQL trực tiếp:

java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{username}, new UserRowMapper());
    }
}
Tóm tắt
Spring cung cấp nhiều cách tiếp cận để xử lý giao tiếp với cơ sở dữ liệu một cách nâng cao như sử dụng Criteria và Query Builder, phân trang, sắp xếp nhiều điều kiện, Specification, và sử dụng Native Query hoặc JdbcTemplate cho các truy vấn tùy chỉnh.
Các kỹ thuật này giúp cho việc thao tác với dữ liệu trong ứng dụng của bạn trở nên linh hoạt và hiệu quả hơn.

  
