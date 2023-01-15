package com.example.SpringBatchTutorial.job.migrationJob.core.domain.accounts;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String orderItem;
	private Integer price;
	private Date orderDate;
	private Date accountDate;
}
