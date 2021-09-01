package com.example.REGISTRATION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.service.BillService;

@RestController
public class BillController {
//	@Autowired
//	private BillRepo billRepo;

	@Autowired
	private BillService billService;

	/* EDIT BILL */
	@PutMapping("/editBill/{id}")
	public ResponseEntity<Bill> editBill(@PathVariable Long id, @RequestBody Bill bill) {
		Bill bill1 = billService.editBill(id, bill);
		return new ResponseEntity<Bill>(bill1, HttpStatus.OK);
	}

	/* CANCEL BILL */
	@PutMapping("/cancelBill/{id}")
	public ResponseEntity<Bill> cancelBill(@PathVariable Long id) {
		return new ResponseEntity<Bill>(billService.cancelBill(id), HttpStatus.OK);
	}

	/* STATISTICS */
	@GetMapping("/statistic/{month}/{year}")
	public int statistic(@PathVariable int month , @PathVariable int year) {
		int totalMoney = billService.totalMoney(month, year);
		return totalMoney;
	}
	
	/* BILLS BY MONTH AND YEAR */
	@GetMapping("/billsByMonthAndYear/{month}/{year}")
	public List<Bill> billsByMonthAndYear(@PathVariable int month , @PathVariable int year){
		List<Bill> billsByMonthAndYear = billService.getAllByMonthAndYear(month, year);
		return billsByMonthAndYear;
	}
}
