package services;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserService {
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
}
