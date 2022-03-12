package com.example.REGISTRATION.config;

import java.util.UUID;

public class Bcrypt {
	public static void main(String[] args) {
		String transactionId = UUID.randomUUID().toString();
		System.out.println(transactionId);
	}
}
