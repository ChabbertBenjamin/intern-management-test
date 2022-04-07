package com.example.repository;

import com.example.model.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternsManagementServiceRepository extends JpaRepository<Intern,Long> {

}
