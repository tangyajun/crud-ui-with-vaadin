package crudwithvaadin.cruduiwithvaadin.web.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author tangyajun
 * @Description TO DO
 * @create 2020-04-14-9:18
 **/
@Entity
@Getter
@Setter
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private Integer gender;

	private Integer age;

	private String phone;

	private String email;

	private String address;

	public Customer() {

	}

	public Customer(String name,int gender,int age,String phone,
	                String email,String address) {
		this.name=name;
		this.gender=gender;
		this.age=age;
		this.phone=phone;
		this.email=email;
		this.address=address;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d,name='%s',gender=%d,age=%d,phone='%s',email='%s',address='%s'",
				id,name,gender,age,phone,email,address);
	}
}
