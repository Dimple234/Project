package com.cloth.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloth.entities.User;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private	com.cloth.Dao.UserDto userDto;
	@Autowired
	private com.cloth.Dao.itemDto itemDto;
	@Autowired
	private com.cloth.Dao.orderDao orderDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	private void name(Model model, Principal principal) {
		String userName = principal.getName();
		 // System.out.println("Username = "+userName);
		  User user =userDto.getUserByUserName(userName);
		  model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String user(Model model, Principal principal) {
		
		  String userName = principal.getName();
		  System.out.println("Username = "+userName);
		  User user =userDto.getUserByUserName(userName);
		  model.addAttribute("user", user);
		
		
		return "User/home";
	   }

	
	
	@GetMapping("/order")
	public String or(Model model,Principal principal) {
		
		ArrayList<com.cloth.entities.product> product= (ArrayList<com.cloth.entities.product>)itemDto.findAll();
	//	System.out.println(item);
		model.addAttribute("item",product);
		
		 String userName = principal.getName();
		  System.out.println("Username = "+userName);
		  User user =userDto.getUserByUserName(userName);
		  model.addAttribute("user", user);
		
		return "User/order";
	}
	@PostMapping("/order/{id}")
	public String order(@PathVariable("id") int product,@ModelAttribute com.cloth.entities.OrderAdmin orderAdmin,Principal principal,Model model) {
			String Uname = principal.getName();
			User u= userDto.getUserByUserName(Uname);
			model.addAttribute("user",u);
			com.cloth.entities.product it= itemDto.getById(product);
			int no_of_order=orderAdmin.getNoOfOrder();
			orderAdmin.setBillAmount((no_of_order*it.getPrice()));
			orderAdmin.setName(Uname);
			orderAdmin.setCid(u.getId());
			orderAdmin.setStatus("pending");
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = currentDateTime.format(formatter);
	      //  System.out.println(formattedDateTime);
	        orderAdmin.setOrderDateTime(formattedDateTime);
	        orderAdmin.setProductname(it.getName());
	        System.out.println(u.getId());
	        orderDao.save(orderAdmin);
	       
			return "redirect:ordernew";
		}
		@GetMapping("/order/ordernew")
		public String or1(Model model,Principal principal) {
			name(model,principal);
			ArrayList<com.cloth.entities.product> item= (ArrayList<com.cloth.entities.product>)itemDto.findAll();
		//	System.out.println(item);
			model.addAttribute("item",item);
			
			return "User/order";
		}
		
		@GetMapping("/updateprofile")
		public String profile(Model model, Principal principal)
		{		
			 name(model,principal);
			
			return"User/update";
		}
		@PostMapping("/updatePr")
		public String updateProfile(@ModelAttribute("user") User updatedUser ,Model model) {
		   
			System.out.println(updatedUser);
//		    	
	        User existingUser = userDto.getUserByUserName(updatedUser.getEmail());
	        
	        existingUser.setName(updatedUser.getName());
	    
//		       
	        userDto.save(existingUser);	
		       
		        return "User/update";
		  
		    
		    
		}
		
		@GetMapping("/Orderhistory")
		public String Order(Model model,Principal principal) {
			String name=principal.getName();
			name(model,principal);
			ArrayList<com.cloth.entities.OrderAdmin> all= new ArrayList<>();
			ArrayList<com.cloth.entities.OrderAdmin> al = (ArrayList<com.cloth.entities.OrderAdmin>) orderDao.findAll();
			for(com.cloth.entities.OrderAdmin o:al) {
				if(o.getName().equals(name)) {
					all.add(o);
				}
			}
			
			model.addAttribute("all",all);
			
			return "User/Orderhistory";
		}
		
		
	
}
