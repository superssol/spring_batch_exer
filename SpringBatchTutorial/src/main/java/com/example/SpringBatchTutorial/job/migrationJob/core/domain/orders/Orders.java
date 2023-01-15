package com.example.SpringBatchTutorial.job.migrationJob.core.domain.orders;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String orderItem;
	private Integer price;
	private Date orderDate;
}
