package com.example.REGISTRATION.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.REGISTRATION.entity.BillDetail;

@Service
public interface BillDetailService {
	List<BillDetail> findByBill(Long id);
}
