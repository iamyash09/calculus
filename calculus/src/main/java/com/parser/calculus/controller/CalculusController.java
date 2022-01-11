package com.parser.calculus.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parser.calculus.service.impl.CalculusServiceImpl;

@RestController
public class CalculusController {
	
	private @Autowired CalculusServiceImpl calculusServiceImpl;
	
	/**
	 * This will perform decode encoded expression, parse and perform arithmetic operation 
	 * 
	 * @param query
	 * @return
	 */
	@GetMapping("/calculus")
	public HashMap<String, Object> calculus(@RequestParam String query) {	
		
		HashMap<String, Object> apiResponse = new HashMap<String, Object>();
		
		try {
			String expression = calculusServiceImpl.decodeExpression(query);
			Double number = calculusServiceImpl.parseExpression(expression);
			
			apiResponse.put("error", false);
			apiResponse.put("result", String.format("%.2f", number));
		}
		catch(Exception e) {
			apiResponse.put("error", true);
			apiResponse.put("message", "Error occured: " + e.getMessage());
		}

		return apiResponse;
	}
}
