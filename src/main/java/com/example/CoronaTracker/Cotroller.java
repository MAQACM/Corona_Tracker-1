package com.example.CoronaTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Cotroller {
	@Autowired
	CoronaDataService coronaDataService;
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("locationStats", coronaDataService.getAllStats());
		model.addAttribute("totalCases",coronaDataService.getTotalCases());
		return "home";
	}

}
