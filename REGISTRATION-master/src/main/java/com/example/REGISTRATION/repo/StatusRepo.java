package com.example.REGISTRATION.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
	Status findStatusByStatusName(String statusName);
	
	Status findStatusById(Long id);
}
