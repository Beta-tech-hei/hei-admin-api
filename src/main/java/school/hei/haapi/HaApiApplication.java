package school.hei.haapi;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import school.hei.haapi.service.EmailSenderService;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class HaApiApplication {
  private EmailSenderService senderService;
  public static void main(String[] args) {
    SpringApplication.run(HaApiApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void send() {
    senderService.sendEmail("hei.nitsiaro@gmail.com","TEST","AONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA HEHE");
  }
}
