package ca.sheridancollege.giramnik.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollege.giramnik.beans.Post;
import ca.sheridancollege.giramnik.beans.Thread;
import ca.sheridancollege.giramnik.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
public class TestController {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private DatabaseAccess databaseAccess;

	@Test
	@WithMockUser
	public void testViewThreadPage() throws Exception {
		this.mockMvc.perform(get("/viewAllThread")).andExpect(status().isOk()).andExpect(view().name("addAllThread"));
	}

	@Test
	public void testRoot() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index.html"));
	}

	@Test
	public void testLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login.html"));
	}

	@Test
	public void testRegisterPage() throws Exception {
		mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(view().name("register"));
	}

	@Test
	public void testAddThread() throws Exception {
		this.mockMvc.perform(get("/addThread")).andExpect(status().isFound());
	}
	@Test
	public void testgetUserPage() throws Exception {
	this.mockMvc.perform(get("/user"))
	.andExpect(status().isFound());
	}
	@Test
	public void testAccessDeniened() throws Exception {
	this.mockMvc.perform(get("/access-denied"))
	.andExpect(status().isFound());
	}
	@Test
	@WithMockUser
	public void testAddThreadToDatabase() {
		Thread thread = new Thread();
		thread.setDate(LocalDate.now());
		thread.setTime(LocalTime.now());
		int origSize = databaseAccess.getAllThreads().size();
		databaseAccess.addThread(thread);
		int newSize = databaseAccess.getAllThreads().size();
		assertThat(newSize).isEqualTo(origSize + 1);
	}
	@Test
	@WithMockUser
	public void testAddPostToDatabase() {
		Post post = new Post();
		post.setThread_id(1L);
	    post.setContent("This is a test post."); 
		int origSize = databaseAccess.getPostsForUser().size();
		databaseAccess.addPost(post);
		int newSize = databaseAccess.getPostsForUser().size();
		assertThat(newSize).isEqualTo(origSize + 1);
	}
	
	@Test
	public void testRegister() throws Exception {
	    mockMvc.perform(get("/register"))
	            .andExpect(status().isOk()) 
	            .andExpect(view().name("register")) 
	            .andExpect(model().attributeExists("user"));
	}
	@Test
	@WithMockUser
	public void testAddThreadWithRedirect() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("username", "user4");
		requestParams.add("title", "Hello");
		requestParams.add("date", "2023-11-27");
		requestParams.add("time", "12:30:00");

		this.mockMvc.perform(post("/addthreads").params(requestParams)).andExpect(status().isFound())
				.andExpect(redirectedUrl("/addThread"));
	}
}