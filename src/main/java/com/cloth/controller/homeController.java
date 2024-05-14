package com.cloth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloth.Dao.UserDto;
import com.cloth.entities.User;
import com.cloth.helper.Message;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class homeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserDto userDto;
	
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("tittle","About- Online Clothes Store");
		return "about";
	}
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("tittle","Register - Online Clothes Store");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,Model model,HttpSession session) {
		try {
			System.out.println("User "+user);
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User result = this.userDto.save(user);
			
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Seccessfully registered","alert-success"));
			return "signup";
			
			}catch (Exception e) {
			//System.out.println(e);
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something Went Wrong !","alert-error"));
			return "signup";
		}
		
	}
	@GetMapping("/signin")
	public String signin() {
		return "login";
	}
}
