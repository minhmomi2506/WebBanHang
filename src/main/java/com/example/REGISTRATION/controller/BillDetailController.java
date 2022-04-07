package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.BillDetail;
import com.example.REGISTRATION.service.BillDetailService;

@RestController
public class BillDetailController {
	@Autowired
	private BillDetailService billDetailService;
	
	@GetMapping("/listBillDetail/{id}")
	public List<BillDetail> getAll(@PathVariable Long id){
		List<BillDetail> billDetails = billDetailService.findByBill(id);
		return billDetails;
	}
}
