package com.sso.SSO_BE_DG2_8.repository;

import com.sso.SSO_BE_DG2_8.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, String> {

}
