package com.sso.repository;

import com.sso.model.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailDetailRepository extends JpaRepository<EmailDetails, Long> {
}
