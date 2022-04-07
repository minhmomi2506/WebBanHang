package com.example.REGISTRATION.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;

@Entity
@Table(name = "bill_detail")
@Data
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "bill_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bill bill;
    
    @Column
    private int productPrice;
    
    @Column
    private int quantity;
    
    @Column
    private int money;
}
