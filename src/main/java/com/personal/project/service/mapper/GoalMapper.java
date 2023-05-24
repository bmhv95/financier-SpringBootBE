package com.personal.project.service.mapper;

import com.personal.project.entity.Goal;
import com.personal.project.service.DTO.GoalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoalMapper {
    GoalDTO goalToGoalDTO(Goal goal);

    List<GoalDTO> goalListToGoalDTOList(List<Goal> goals);

    @Mapping(target = "goalID", ignore = true)
    void updateGoal(GoalDTO goalDTO, @MappingTarget Goal goal);
}
