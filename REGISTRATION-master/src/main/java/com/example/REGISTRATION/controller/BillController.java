package com.example.REGISTRATION.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.repo.BillRepo;
import com.example.REGISTRATION.service.BillService;

@RestController
public class BillController {
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private BillService billService;
	
	@PostMapping("/editBill/{id}/{status}")
	public void editBill(@PathVariable Long id , @PathVariable String status) {
		Bill bill = billRepo.findBillById(id);
		billService.editBill(bill.getId(), status);
	}
}
