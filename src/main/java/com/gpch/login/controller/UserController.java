package com.gpch.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gpch.login.model.EmployeeModel;
import com.gpch.login.model.User;
import com.gpch.login.service.UserService;


@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	 @GetMapping(value = "/{pageNumber}")
	    public String list(@PathVariable Integer pageNumber, Model model) {
	        Page<User> page = userService.getList(pageNumber);

	        int current = page.getNumber() + 1;
	        int begin = Math.max(1, current - 5);
	        int end = Math.min(begin + 10, page.getTotalPages());

	        model.addAttribute("list", page);
	        model.addAttribute("beginIndex", begin);
	        model.addAttribute("endIndex", end);
	        model.addAttribute("currentIndex", current);

	        return "users/list";

	    }

	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", new User());
		mv.setViewName("users/form");
		return mv;
	}
	
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", userService.findById(id));
		mv.setViewName("users/form");
		return mv;
	}
		
	@RequestMapping(value = "/merge",method = RequestMethod.POST)
	public String merge(User user,RedirectAttributes ra) {

		Object status = null;
		try {
				if(user.getId() == null) {
					User newUser = new User();
					newUser.setEmail(user.getEmail());
					newUser.setName(user.getName());
					newUser.setLastName(user.getLastName());
					newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
					userService.save(newUser);
				}else {
					User oldUser = userService.findById(user.getId());
					oldUser.setEmail(user.getEmail());
					oldUser.setName(user.getName());
					oldUser.setLastName(user.getLastName());
					userService.saveUser(oldUser);
				}
				
			ra.addFlashAttribute("successFlash","success for save data");
			return "redirect:/user/1";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return "users/form";
	}
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String delete(@PathVariable long id,RedirectAttributes ra) {
		userService.delete(id);
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/user/1";
	}
	
	
}
