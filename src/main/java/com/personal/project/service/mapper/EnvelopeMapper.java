package com.personal.project.service.mapper;

import com.personal.project.entity.Envelope;
import com.personal.project.entity.Goal;
import com.personal.project.entity.Spending;
import com.personal.project.service.DTO.EnvelopeDTO;
import com.personal.project.service.DTO.GoalDTO;
import com.personal.project.service.DTO.SpendingDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EnvelopeMapper {
    SpendingDTO toSpendingDTO(Spending spending);
    List<SpendingDTO> toSpendingDTOs(List<Spending> spending);
    @Mapping(target = "ID", ignore = true)
    void updateSpending(SpendingDTO spendingDTO, @MappingTarget Spending spending);

    GoalDTO toGoalDTO(Goal goal);
    List<GoalDTO> toGoalDTOs(List<Goal> goal);
    @Mapping(target = "ID", ignore = true)
    void updateGoal(GoalDTO goalDTO, @MappingTarget Goal goal);

    default String getType(Envelope envelope){
        if (envelope instanceof Goal){
            return "GOAL";
        }
        if (envelope instanceof Spending){
            return "SPENDING";
        }
        return null;
    }

    default <T extends EnvelopeDTO> T map(Envelope envelope){
        if(envelope instanceof Goal){
            return (T) toGoalDTO((Goal) envelope);
        }
        if(envelope instanceof Spending){
            return (T) toSpendingDTO((Spending) envelope);
        }
        return null;
    }
}
