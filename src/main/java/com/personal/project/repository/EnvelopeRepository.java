package com.personal.project.repository;

import com.personal.project.entity.Envelope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvelopeRepository extends JpaRepository<Envelope, Long> {
}
