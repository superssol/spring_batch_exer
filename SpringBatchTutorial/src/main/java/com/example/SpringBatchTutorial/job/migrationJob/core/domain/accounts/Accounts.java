package com.example.SpringBatchTutorial.job.migrationJob.core.domain.accounts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.SpringBatchTutorial.job.migrationJob.core.domain.orders.Orders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor
public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String orderItem;
	private Integer price;
	private Date orderDate;
	private Date accountDate;
	
	public Accounts(Orders orders) {
		this.id = orders.getId();
		this.orderItem = orders.getOrderItem();
		this.price = orders.getPrice();
		this.orderDate = orders.getOrderDate();
		this.accountDate = new Date();
	}
}
