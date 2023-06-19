package com.personal.project.repository;

import com.personal.project.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GoalRepository extends EnvelopeRepository<Goal> {
}
