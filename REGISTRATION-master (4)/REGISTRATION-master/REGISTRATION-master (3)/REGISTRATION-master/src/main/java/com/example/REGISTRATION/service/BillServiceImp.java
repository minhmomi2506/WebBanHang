package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.BillRepo;

public class BillServiceImp implements BillService {
	
	@Autowired
	private BillRepo billRepo;

	@Override
	public List<Bill> getByUser(User user) {
		 return billRepo.findByUser(user);
	}

}
