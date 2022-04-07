package com.example.REGISTRATION.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.REGISTRATION.entity.Bill;
import com.example.REGISTRATION.entity.BillDetail;

@Repository
public interface BillDetailRepo extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findByBill(Bill bill);
}
