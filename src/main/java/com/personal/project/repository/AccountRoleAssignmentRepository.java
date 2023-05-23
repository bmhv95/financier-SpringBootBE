package com.personal.project.repository;

import com.personal.project.entity.AccountRoleAssignment;
import com.personal.project.entity.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRoleAssignmentRepository extends JpaRepository<AccountRoleAssignment, Long> {
    Optional<AccountRoleAssignment> findByRole(EnumRole role);
}
