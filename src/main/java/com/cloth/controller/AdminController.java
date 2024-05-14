package com.cloth.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloth.Dao.itemDto;
import com.cloth.entities.OrderAdmin;
import com.cloth.entities.product;










@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private com.cloth.Dao.orderDao orderDao;
	@Autowired
	private itemDto itemDto;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	static int Id;
	@Autowired
	private com.cloth.Dao.UserDto userDto;
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("Username = "+userName);
		
		com.cloth.entities.User user = userDto.getUserByUserName(userName);
		model.addAttribute("user", user);
		
	}
	@GetMapping("/index")	                                      
	public String user(Model model, Principal principal) {
		
		String userName = principal.getName();
		System.out.println("Username = "+userName);
		com.cloth.entities.User user = userDto.getUserByUserName(userName);                                                                   
		model.addAttribute("user", user);
		System.out.println(user);
		ArrayList<product> a= (ArrayList<product>) itemDto.findAll();
		model.addAttribute("item",a);
		return "Admin/dashboard";
	   }
	
	@RequestMapping("/addcategory")
	public String oprnCategoryForm(Model model){
		model.addAttribute("item",new product());
		return "Admin/categoryForm";
		}
	
	
	@PostMapping("/processcategory")
	public String processContact(@ModelAttribute  product product , @RequestParam("itemimage") MultipartFile file ) {
		System.out.println(product );
		try {
		if(file.isEmpty()) {
			System.out.println("Empty");
		}else {
			product .setImage(file.getOriginalFilename());
			
			File fileSave = new ClassPathResource("static/images").getFile();
			
			Path path = Paths.get(fileSave.getAbsolutePath()+ File.separator +file.getOriginalFilename());
			
			Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
		}
		
		itemDto.save(product );
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return "Admin/categoryForm";
	}
	
	
	@GetMapping("/updateprofile")
	public String profile(Model model, Principal principal)
	{		
		
		return"Admin/update";
	}
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("user") com.cloth.entities.User updatedUser ,Model model) {
	    try {
	    	
	        com.cloth.entities.User existingUser = userDto.getUserByUserName(updatedUser.getEmail());

	        // Update only the fields you want to allow modification
	        existingUser.setName(updatedUser.getName());
	      //  existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
	        existingUser.setEmail(updatedUser.getEmail());
	        // Add other fields as needed
	        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
	        System.out.println(existingUser);
	        
	     // userDto.save(existingUser);	
	        model.addAttribute("successMessage", "Profile updated successfully!");
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "Error updating profile: " + e.getMessage());
	    }
	    
	    return "Admin/update";
	}
	
	
	
	
	@PostMapping("/deleteItem/{id}")
	public String deleteItem(@PathVariable("id") int item) {
		System.out.println(item);
		itemDto.deleteById(item);
		
		
		return "redirect:dash";
	}

	@GetMapping("/deleteItem/dash")	                                      
	public String use(Model model, Principal principal) {
		
		String userName = principal.getName();
		System.out.println("Username = "+userName);
		com.cloth.entities.User user = userDto.getUserByUserName(userName);                                                                   
		model.addAttribute("user", user);
		System.out.println(user);
		ArrayList<product> a= (ArrayList<product>) itemDto.findAll();
		model.addAttribute("item",a);
		return "Admin/dashboard";
	   }

	   
		@GetMapping("/Allusers")
		public String Allusers(Model model) {
			ArrayList<com.cloth.entities.User> all=new ArrayList<>();
			ArrayList<com.cloth.entities.User> al= (ArrayList<com.cloth.entities.User>) userDto.findAll();
			for(com.cloth.entities.User u:al) {
				if(u.getRole().equals("ROLE_USER")) {
					all.add(u);
				}
			}
			model.addAttribute("all",all);
			return "Admin/Allusers";
		}
		@PostMapping("/Allusers/{id}")
		public String deleteuser(@PathVariable("id")int id, Model model) {
			userDto.deleteById(id);
			return "redirect:Allusers";
		}
		@GetMapping("Allusers/Allusers")
		public String users(Model model) {
			ArrayList<com.cloth.entities.User> all= (ArrayList<com.cloth.entities.User>) userDto.findAll();
			model.addAttribute("all",all);
			return "Admin/Allusers";
		}

		

@GetMapping("/order")
public String Order(Model model) {
	
	ArrayList<com.cloth.entities.OrderAdmin> all = (ArrayList<com.cloth.entities.OrderAdmin>) orderDao.findAll();
	model.addAttribute("all",all);
	
	return "Admin/AllOrders";
	
	}

@RequestMapping("/payment/{id}")
public String pay(@PathVariable("id") int id ,@ModelAttribute("id") OrderAdmin orderAdmin ,Model model) {
	Id=id;
com.cloth.entities.OrderAdmin all= orderDao.getById(id);
//System.out.println(all);
model.addAttribute("c",all);
	
	return "Admin/pay";

}

@PostMapping("/payment")
public String updatePay(@ModelAttribute com.cloth.entities.OrderAdmin order ,Model model) {
System.out.println("Orrrrr = "+order);	
com.cloth.entities.OrderAdmin all= orderDao.getById(Id);
all.setPaymentMethod(order.getPaymentMethod());

all.setStatus("Done");
orderDao.save(all);
System.out.println(all);
model.addAttribute("c",all);
	return "redirect:payment/order";
}

@GetMapping("payment/order")
public String paymentOrder(Model model) {
	
	ArrayList<com.cloth.entities.OrderAdmin> all = (ArrayList<com.cloth.entities.OrderAdmin>) orderDao.findAll();
	model.addAttribute("all",all);
	
	return "Admin/AllOrders";
	
}
}
