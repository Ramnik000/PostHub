package ca.sheridancollege.giramnik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.giramnik.beans.Post;
import ca.sheridancollege.giramnik.beans.Thread;
import ca.sheridancollege.giramnik.beans.UserRegistration;
import ca.sheridancollege.giramnik.database.DatabaseAccess;

// I have changed my port from 8080 to 8843

@Controller
public class MyController {

	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	private DatabaseAccess databaseAccess;

	@GetMapping("/user")
	public String getuserPage(Model model, @ModelAttribute Thread thread) {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Thread> threads = databaseAccess.getThreadsForUser(loggedInUsername);
		model.addAttribute("threads", threads);
		return "users/index.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@GetMapping("/")
	public String root() {
		return "index.html";
	}

	@GetMapping("/register")
	public String register(Model model, UserRegistration user) {
		model.addAttribute("user", user);
		return "register";
	}
	

	@PostMapping("/register")
	public String processRegister(@ModelAttribute UserRegistration user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getAuthority().equalsIgnoreCase("user")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else if (user.getAuthority().equalsIgnoreCase("manager")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		User newuser = new User(user.getUsername(), encodedPassword, authorities);
		jdbcUserDetailsManager.createUser(newuser);
		return "redirect:/";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/error/access-denied.html";
	}

	@GetMapping("/addThread")
	public String addnewThread(Model model, @ModelAttribute Thread thread) {
		model.addAttribute("thread", thread);
		return "addThread.html";
	}

	@GetMapping("/viewAllThread")
	public String viewAllThreads(Model model) {
		ArrayList<Thread> threads = databaseAccess.getAllThreads();
		model.addAttribute("threads", threads);
		return "addAllThread";
	}

	@PostMapping("/addthreads")
	public String processAddThread(@ModelAttribute Thread thread, Model model) {
		databaseAccess.addThread(thread);
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Thread> threads = databaseAccess.getThreadsForUser(loggedInUsername);
		model.addAttribute("threads", threads);
		model.addAttribute("threadlist", threads);
		return "redirect:/addThread";
	}

	@GetMapping("/post/{id}")
	public String getPostById(Model model, @PathVariable Long id) {
		model.addAttribute("posts", databaseAccess.getPostsForThread(id));
		Post post = new Post();
		post.setThread_id(id);
		model.addAttribute("post",post);
		model.addAttribute("title", databaseAccess.getthreadNameByID(id));
		return "viewPost.html";
	}
	
	@PostMapping("/post")
	public String processAddPost(@ModelAttribute Post post) {
		System.out.println(post);
		databaseAccess.addPost(post);
		return "redirect:/post/"+post.getThread_id();
	}

	@GetMapping("/addpost")
	public String addnewPost(Model model, @ModelAttribute Post posts) {
		model.addAttribute("posts", posts);

		return "addPost.html";
	}


	@GetMapping("/delete/{id}")
	public String deletebyID(@PathVariable Long id) {
		databaseAccess.deleteThreadByID(id);
		return "redirect:/user";
	}

}