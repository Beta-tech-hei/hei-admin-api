package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.DelayPenalty;

@Component
public class DelayPenaltyMapper {

    public school.hei.haapi.endpoint.rest.model.DelayPenalty toRest(DelayPenalty delayPenalty) {
        var restDelayPenalty = new school.hei.haapi.endpoint.rest.model.DelayPenalty();
        restDelayPenalty.setId(delayPenalty.getId());
        restDelayPenalty.setInterestPercent(delayPenalty.getInterestPercent());
        restDelayPenalty.setInterestTimerate(delayPenalty.getInterestTimerate());
        restDelayPenalty.setGraceDelay(delayPenalty.getGraceDelay());
        restDelayPenalty.setApplicabilityDelayAfterGrace(delayPenalty.getApplicabilityDelayAfterGrace());
        restDelayPenalty.setCreationDatetime(delayPenalty.getCreationDatetime());
        return restDelayPenalty;
    }

    public DelayPenalty toDomain(school.hei.haapi.endpoint.rest.model.DelayPenalty restDelayPenalty) {
        return DelayPenalty.builder()
                .id(restDelayPenalty.getId())
                .interestPercent(restDelayPenalty.getInterestPercent())
                .interestTimerate(restDelayPenalty.getInterestTimerate())
                .graceDelay(restDelayPenalty.getGraceDelay())
                .applicabilityDelayAfterGrace(restDelayPenalty.getApplicabilityDelayAfterGrace())
                .creationDatetime(restDelayPenalty.getCreationDatetime())
                .build();
    }
}
