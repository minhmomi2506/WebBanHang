package com.example.REGISTRATION.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.BillDetail;
import com.example.REGISTRATION.repo.BillDetailRepo;
import com.example.REGISTRATION.repo.BillRepo;
import com.example.REGISTRATION.service.BillDetailService;

@Component
public class BillDetailServiceImp implements BillDetailService {
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private BillDetailRepo billDetailRepo;

	@Override
	public List<BillDetail> findByBill(Long id) {
		// TODO Auto-generated method stub
		Bill bill = billRepo.findBillById(id);
		List<BillDetail> billDetails = billDetailRepo.findByBill(bill);
		return billDetails;
	}

}
