package school.hei.haapi.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.Fee;
import school.hei.haapi.model.Payment;
import school.hei.haapi.repository.FeeRepository;
import school.hei.haapi.repository.PaymentRepository;
import school.hei.haapi.service.DelayPenaltyService;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {
    private final EmailSenderService emailSenderService;
    private final DelayPenaltyService delayPenaltyService;
    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;

    public void latePaymentNotificationEmail(String email, int numberDayLate) {
        emailSenderService.sendEmail(
                email,
                "RETARD DE PAYEMENT",
                "retard de " + numberDayLate
        );
    }

    public void delayedChecker(Fee fee) {
        DelayPenalty delayPenalty = delayPenaltyService.getDelayPenalty();
        List<Payment> paymentList = paymentRepository.getByFee_Id(fee.getId()).stream()
                .sorted(Comparator.comparing(Payment::getCreationDatetime).reversed())
                .collect(Collectors.toUnmodifiableList());
        List<Payment> paymentListVerified = (paymentList.size() > 0) ? paymentList : null;
        Date now = Date.from(Instant.now());
        Date lastTransaction = (paymentListVerified == null) ? Date.from(fee.getCreationDatetime()) : Date.from(paymentListVerified.get(0).getCreationDatetime());
        if (now.getMonth() - lastTransaction.getMonth() == 1) {
            int dif1 = now.getDate() - delayPenalty.getApplicabilityDelayAfterGrace();
            int dif2 = now.getDate() - delayPenalty.getGraceDelay();
            if (dif1 > 0) {
                this.latePaymentNotificationEmail(
                        fee.getStudent().getEmail(),
                        dif1
                );
            } else if (dif2 > 0) {
                this.latePaymentNotificationEmail(
                        fee.getStudent().getEmail(),
                        dif2
                );
            }
        }
    }
    // TODO : CHECK ONLY WHAT HAS A STATUS = LATE
    public void delayedCheckerCheckerList(List<Fee> feeList) {
        feeList.forEach(this::delayedChecker);
    }

    public void delayedCheckerCheckerListFee() {
        List<Fee> feeList = feeRepository.findAll();
        this.delayedCheckerCheckerList(feeList);
    }
}
