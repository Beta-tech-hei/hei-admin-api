package school.hei.haapi.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.Fee;
import school.hei.haapi.repository.FeeRepository;
import school.hei.haapi.service.DelayPenaltyService;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final EmailSenderService emailSenderService;
    private final DelayPenaltyService delayPenaltyService;
    private final FeeRepository feeRepository;

    public void latePaymentNotificationEmail(String email, int numberDayLate) {
        emailSenderService.sendEmail(
                email,
                "RETARD DE PAYEMENT",
                "retard de " + numberDayLate
        );
    }

    public void delayedChecker(Fee fee) {
        DelayPenalty delayPenalty = delayPenaltyService.getDelayPenalty();
        Date now = Date.from(Instant.now());
        int dif1 = now.getDay() - delayPenalty.getApplicabilityDelayAfterGrace();
        if (dif1 > 0) {
            this.latePaymentNotificationEmail(
                    fee.getStudent().getEmail(),
                    dif1
            );
        }
    }

    public void delayedCheckerCheckerList(List<Fee> feeList) {
        feeList.forEach(this::delayedChecker);
    }

    public void delayedCheckerCheckerListFee() {
        System.out.println("=====================================================================================================================================");
        List<Fee> feeList = feeRepository.findAll();
        this.delayedCheckerCheckerList(feeList);
        System.out.println("=====================================================================================================================================");
    }

}
