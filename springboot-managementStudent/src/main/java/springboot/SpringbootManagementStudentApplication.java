package springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class SpringbootManagementStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootManagementStudentApplication.class, args);
    }

}