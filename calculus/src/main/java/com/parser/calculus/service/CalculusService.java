package com.parser.calculus.service;

public interface CalculusService {
	public String decodeExpression(String query) throws Exception;
	public Double parseExpression(String expression) throws Exception;
}
