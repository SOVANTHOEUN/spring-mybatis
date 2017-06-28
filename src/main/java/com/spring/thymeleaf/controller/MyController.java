package com.spring.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
	
	@RequestMapping({"/dashboard","/"})
	public String Dashboard(Model model){
		model.addAttribute("LIST","/dashboard");
		return "dashboard";
	}
	
	@RequestMapping("/user-cu")
	public String Userpage(Model model){
		model.addAttribute("LIST","/dashboard");
		return "user-cu";
	}
//	@RequestMapping(value="USER_CU", method=RequestMethod.POST)
//	public String home(@RequestParam int Id, String Username, String Email, String Password, Model model){
//		model.addAttribute("LIST","/dashboard");
//		model.addAttribute("ID",Id);
//		model.addAttribute("USERNAME",Username);
//		model.addAttribute("EMAIL",Email);
//		model.addAttribute("PASSWORD",Password);
//		return "user_cu";
//	}

	@RequestMapping("/user-list")
	public String Userlistpage(Model model){
		model.addAttribute("LIST","/dashboard");
		model.addAttribute("ID","");
		model.addAttribute("USERNAME","");
		model.addAttribute("EMAIL","");
		model.addAttribute("PASSWORD","");
		return "user-list";
	}


	@RequestMapping("/role-cu")
	public String Rolepage(){
		return "role-cu";
	}
	
	@RequestMapping("/role-list")
	public String Rolelistpage(){
		return "role-list";
	}
}






