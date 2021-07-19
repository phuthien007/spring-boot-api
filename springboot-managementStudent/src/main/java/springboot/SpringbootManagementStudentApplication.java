package springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableJpaRepositories
public class SpringbootManagementStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootManagementStudentApplication.class, args);
    }

}