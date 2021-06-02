package com.example.REGISTRATION.service;

import java.util.List;

import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.User;

public interface BillService {
	List<Bill> getByUser(User user);
}
