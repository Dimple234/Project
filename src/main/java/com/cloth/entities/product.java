package com.cloth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "name", nullable = false)
	private String  name;
	@Column(name = "price", nullable = false)
	private Integer price;
	private String  discription;
	private String  image ;
	

	public product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public product(Integer id, String name, Integer price, String discription, String image) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.discription = discription;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "item [id=" + id + ", name=" + name + ", price=" + price + ", discription=" + discription + ", image="
				+ image + "]";
	}
	

}


