package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;

@Service
public interface BillService {
	List<Bill> getAll();
	
	List<Bill> findByUser(User user);
	
	Bill editBill(Long id , Bill bill);
	
	Bill cancelBill(Long id);
	
	int totalMoney(int month , int year);
	
	List<Bill> getAllByMonthAndYear(int month , int year);
}
