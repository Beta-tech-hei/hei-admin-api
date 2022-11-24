package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.validator.DelayPenaltyValidator;
import school.hei.haapi.repository.DelayPenaltyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DelayPenaltyService {
    private DelayPenaltyRepository delayPenaltyRepository;
    private DelayPenaltyValidator delayPenaltyValidator;

    public List<DelayPenalty> getAll() {
        return delayPenaltyRepository.findAll();
    }

    public DelayPenalty getById(String id) {
        return delayPenaltyRepository.getById(id);
    }

    public DelayPenalty getDelayPenalty() {
        return delayPenaltyRepository.findAll().get(0);
    }

    public DelayPenalty save(DelayPenalty delayPenalty) {
        delayPenaltyValidator.accept(delayPenalty);
        delayPenalty
                .setId(this.getDelayPenalty().getId());
        return delayPenaltyRepository.save(delayPenalty);
    }
}
