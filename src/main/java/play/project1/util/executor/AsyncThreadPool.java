package play.project1.util.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncThreadPool {

    private static final Integer THREAD_POOL_SIZE = 30;

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
}
