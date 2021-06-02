package com.example.REGISTRATION.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
	public List<Bill> findByUser(User user);
}
