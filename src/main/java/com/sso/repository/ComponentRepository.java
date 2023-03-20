package com.sso.repository;

import com.sso.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, String> {
    List<Component> findComponentsByUsersUuid(String uuid);
}
