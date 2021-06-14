package com.example.REGISTRATION.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
	public List<Bill> findByUser(User user);
	
	Bill findBillById(Long id);
	
	@Query("delete from Bill where id = ?1")
	@Transactional
	@Modifying
	void deleteBillById(Long id);
}
