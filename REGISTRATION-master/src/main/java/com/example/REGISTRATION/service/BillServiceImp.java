package com.example.REGISTRATION.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.Product;
import com.example.REGISTRATION.entity.Status;
import com.example.REGISTRATION.entity.User;
import com.example.REGISTRATION.repo.BillRepo;
import com.example.REGISTRATION.repo.StatusRepo;

@Component
@Transactional
public class BillServiceImp implements BillService {

	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private StatusRepo statusRepo;

	@Override
	public List<Bill> getAll() {
		return billRepo.findAll();
	}

	@Override
	public List<Bill> findByUser(User user) {
		return billRepo.findByUser(user);
	}

	@Override
	public Bill editBill(Long id, Bill bill) {
			Bill bill1 = billRepo.findBillById(id);
			bill1.setStatus(bill.getStatus());
			billRepo.save(bill1);
			return bill1;
	}
	
	@Override
	public Bill cancelBill(Long id) {
		// TODO Auto-generated method stub
		Bill bill = billRepo.findBillById(id);
		Status status = statusRepo.findStatusById(5L);
		bill.setStatus(status);
		Product product = bill.getProduct();
		product.setNumber(product.getNumber() + bill.getQuantity());
		billRepo.save(bill);
		return bill;
	}
	
//	@Override
//	public List<BillGroupby> groupBy() {
//		// TODO Auto-generated method stub
//		return billRepo.groupBy();
//	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int totalMoney(int month, int year) {
		// TODO Auto-generated method stub
		List<Bill> bills = billRepo.findAll();
		int totalMoney = 0;
		for(Bill bill : bills) {
			if(bill.getStatus().getStatusName().equalsIgnoreCase("Giao hàng thành công") && bill.getBuyDate().getMonth() - month + 1 == 0 && bill.getBuyDate().getYear() - year + 1900 == 0) {
				totalMoney = totalMoney + bill.getToTal();
			}
		}
		return totalMoney;
	}
	
@SuppressWarnings("deprecation")
@Override
	public List<Bill> getAllByMonthAndYear(int month , int year) {
		List<Bill> bills = billRepo.findAll();
		List<Bill> billsByMonthAndYear = new ArrayList<Bill>();
		for(Bill bill : bills) {
			if(bill.getStatus().getStatusName().equalsIgnoreCase("Giao hàng thành công") && bill.getBuyDate().getMonth() - month + 1 == 0 && bill.getBuyDate().getYear() - year + 1900 == 0) {
				billsByMonthAndYear.add(bill);
			}
		}
		return billsByMonthAndYear;
		
	}

}
