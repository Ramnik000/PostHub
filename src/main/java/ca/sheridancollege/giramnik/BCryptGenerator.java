package ca.sheridancollege.giramnik;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			String password = "password";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			System.out.println(hashedPassword);
			}
			}
	}


