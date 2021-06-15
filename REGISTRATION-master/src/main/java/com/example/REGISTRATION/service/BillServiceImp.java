package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.BillRepo;

@Component
public class BillServiceImp implements BillService {

	@Autowired
	private BillRepo billRepo;

	@Override
	public List<Bill> getAll() {
		return billRepo.findAll();
	}

	@Override
	public List<Bill> findByUser(User user) {
		return billRepo.findByUser(user);
	}

	@Override
	public void editBill(Long id, String status) {
		Bill bill = billRepo.findBillById(id);
		bill.setStatus(status);
		billRepo.save(bill);
	}
	
	@Override
	public void deleteBill(Long id) {
		billRepo.deleteBillById(id);
	}

}
