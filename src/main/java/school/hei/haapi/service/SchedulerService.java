package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerService {
    private EmailSenderService emailSenderService;

    @Scheduled(cron = "0 18 18 ? * *")
    public void crontab() {
        this.sendMail();
    }

    public void sendMail() {
        emailSenderService.sendEmail("hei.nitsiaro@gmail.com", "TEST", "AONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA HEHE");
    }
}
