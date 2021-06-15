package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;

@Service
public interface BillService {
	List<Bill> getAll();
	
	List<Bill> findByUser(User user);
	
	void editBill(Long id , String status);
	
	void deleteBill(Long id);
}
