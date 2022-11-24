package school.hei.haapi.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerService {
    private NotificationService notificationService;

    @Scheduled(cron = "0 00 23 ? * *")
    public void crontab() {
        notificationService.delayedCheckerCheckerListFee();
    }
}
