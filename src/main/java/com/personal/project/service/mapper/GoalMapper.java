package com.personal.project.service.mapper;

import com.personal.project.entity.Goal;
import com.personal.project.service.DTO.GoalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    @Mapping(source = "account.accountID", target = "accountID")
    GoalDTO goalToGoalDTO(Goal goal);

    List<GoalDTO> goalListToGoalDTOList(List<Goal> goals);
}
