RxJava là một thư viện lập trình phản ứng (reactive programming) cho Java, được phát triển bởi Netflix.
  Nó giúp dễ dàng tạo ra các ứng dụng không chặn (non-blocking) và xử lý luồng dữ liệu không đồng bộ một cách hiệu quả.
  RxJava dựa trên các nguyên tắc của ReactiveX, một API lập trình phản ứng đa ngôn ngữ.

Các thành phần chính của RxJava:
Observable: Đại diện cho một luồng dữ liệu có thể phát ra các item.
Observer: Nhận các item được phát ra bởi Observable.
Operators: Các phương thức để thao tác, chuyển đổi, và kết hợp các luồng dữ liệu.
Scheduler: Quản lý các luồng xử lý và lập lịch thực thi.
1. Cài đặt RxJava
Thêm dependency RxJava vào pom.xml:

<dependency>
    <groupId>io.reactivex.rxjava2</groupId>
    <artifactId>rxjava</artifactId>
    <version>2.2.21</version>
</dependency>
2. Các khái niệm cơ bản
Tạo một Observable
Observable là nguồn phát dữ liệu, nó có thể phát ra một hoặc nhiều item và sau đó kết thúc hoặc phát ra lỗi.

import io.reactivex.Observable;

public class RxJavaExample {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello", "RxJava", "World");
        observable.subscribe(System.out::println);
    }
}
Observer
Observer là thành phần nhận dữ liệu từ Observable.

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CustomObserver implements Observer<String> {
    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("Subscribed!");
    }

    @Override
    public void onNext(String s) {
        System.out.println("Received: " + s);
    }

    @Override
    public void onError(Throwable e) {
        System.err.println("Error: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Completed!");
    }
}
Kết nối Observer với Observable
public class RxJavaExample {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello", "RxJava", "World");
        CustomObserver observer = new CustomObserver();
        observable.subscribe(observer);
    }
}
3. Các Operator phổ biến
Operators trong RxJava cho phép bạn thao tác và chuyển đổi các luồng dữ liệu.

Map
Chuyển đổi từng item trong Observable.

Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
observable.map(item -> item * 2)
          .subscribe(System.out::println);
Filter
Lọc các item trong Observable dựa trên một điều kiện.

Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
observable.filter(item -> item % 2 == 0)
          .subscribe(System.out::println);
FlatMap
Chuyển đổi mỗi item trong Observable thành một Observable mới và sau đó gộp chúng lại.
Observable<String> observable = Observable.just("Hello", "RxJava");
observable.flatMap(item -> Observable.fromArray(item.split("")))
          .subscribe(System.out::println);
4. Scheduler
Schedulers trong RxJava quản lý các luồng xử lý và lập lịch thực thi các task không đồng bộ.
import io.reactivex.schedulers.Schedulers;

public class RxJavaExample {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello", "RxJava", "World");
        observable.subscribeOn(Schedulers.io()) // Đặt việc xử lý Observable trên một luồng I/O
                  .observeOn(Schedulers.single()) // Quan sát kết quả trên một luồng đơn
                  .subscribe(System.out::println);
    }
}
5. Kết hợp RxJava với Spring WebFlux
Bạn có thể sử dụng RxJava với Spring WebFlux để xây dựng các ứng dụng web không đồng bộ và phản ứng.

Thêm dependency cho WebFlux và RxJava
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>io.reactivex.rxjava2</groupId>
    <artifactId>rxjava</artifactId>
    <version>2.2.21</version>
</dependency>
<dependency>
    <groupId>io.reactivex.rxjava2</groupId>
    <artifactId>rxjava2-reactor-adapter</artifactId>
    <version>2.1.1</version>
</dependency>
Tạo một REST controller sử dụng RxJava
import io.reactivex.Observable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.adapter.rxjava.RxJava2Adapter;

@RestController
@RequestMapping("/api")
public class WebFluxRxJavaController {

    @GetMapping("/rxjava")
    public Mono<String> getRxJava() {
        Observable<String> observable = Observable.just("Hello", "RxJava", "with", "WebFlux");
        return RxJava2Adapter.observableToMono(observable.reduce((s1, s2) -> s1 + " " + s2));
    }
}
Tổng kết
RxJava cung cấp các công cụ mạnh mẽ để làm việc với lập trình phản ứng và xử lý luồng dữ liệu không đồng bộ.
Các thành phần chính như Observable, Observer, Operators, và Schedulers giúp xây dựng các ứng dụng không chặn và hiệu quả.
Kết hợp RxJava với Spring WebFlux có thể xây dựng các ứng dụng web phi đồng bộ và phản ứng một cách mạnh mẽ.
Sử dụng RxJava, bạn có thể dễ dàng xử lý dữ liệu luồng và sự kiện, quản lý các luồng xử lý và lập lịch thực thi một cách hiệu quả.
