package app.backend.itemservice.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/item-service")
@RequiredArgsConstructor
public class ItemController {

	@GetMapping("/test")
	public String test() {

		return "item-service test api";
	}

}
