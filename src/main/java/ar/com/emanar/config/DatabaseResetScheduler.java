package ar.com.emanar.config;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseResetScheduler {
	private final ApplicationContext applicationContext;

    @Scheduled(fixedRate = 3600000)
    public void resetDatabase() {
        ((ConfigurableApplicationContext) applicationContext).close();
        System.exit(0);
    }
}
