package app.backend.userservice.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user-service")
@RequiredArgsConstructor
public class UserController {

	@GetMapping("/test")
	public String test() {

		return "user-service api test";
	}

}
