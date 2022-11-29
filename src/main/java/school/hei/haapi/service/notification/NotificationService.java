package school.hei.haapi.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.Fee;
import school.hei.haapi.model.Payment;
import school.hei.haapi.repository.FeeRepository;
import school.hei.haapi.repository.PaymentRepository;
import school.hei.haapi.service.DelayPenaltyService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
        Calendar calendar = Calendar.getInstance();
        Calendar lastTransaction = (paymentListVerified == null) ?
                GregorianCalendar.from(ZonedDateTime.ofInstant(fee.getCreationDatetime(), ZoneId.systemDefault())) :
                GregorianCalendar.from(ZonedDateTime.ofInstant(paymentListVerified.get(0).getCreationDatetime(), ZoneId.systemDefault()));
        if (calendar.get(Calendar.MONTH) - lastTransaction.get(Calendar.MONTH) == 1) {
            System.out.println("==============================================================================================================================");
            System.out.println("NOW MONTH => "+ calendar.get(Calendar.MONTH));
            System.out.println("LAST TRANSACTION => "+ lastTransaction.get(Calendar.MONTH));
            System.out.println("==============================================================================================================================");
            int dif1 = calendar.get(Calendar.DAY_OF_MONTH) - delayPenalty.getApplicabilityDelayAfterGrace();
            int dif2 = calendar.get(Calendar.DAY_OF_MONTH) - delayPenalty.getGraceDelay();
            System.out.println("NOW DAY => " + calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println("==============================================================================================================================");
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
