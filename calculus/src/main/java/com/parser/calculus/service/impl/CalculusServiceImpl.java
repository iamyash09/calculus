package com.parser.calculus.service.impl;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.parser.calculus.service.CalculusService;

/**
 * Service class to for calculus
 * 
 * @author yashvant.dewangan
 *
 */
@Service
public class CalculusServiceImpl implements CalculusService{

	/**
	 * This will decode the expression
	 * 
	 */
	@Override
	public String decodeExpression(String query) throws Exception {
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(query);
			String decodedString = new String(decodedBytes);
			return decodedString;	
		}
		catch(Exception e) {
			throw new Exception("Expression encode error");
		}
	}
	
	/**
	 * This will parse the give input string and perform arithmetic operation
	 * 
	 */
	@Override
	public Double parseExpression(String expression) throws Exception {
		
		if (expression == null || expression.length() == 0) {
            throw new Exception("Expression has null value");
        }
		
        return calc(expression.replace(" ", ""));
	}
	
	/**
	 * Read expression string sequentially and calculate values
	 * 
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	private Double calc(String expression) throws Exception {
		
		try {
			// Will check top precedence first ( )
			if (expression.startsWith("(") && expression.endsWith(")")) {
	            return calc(expression.substring(1, expression.length() - 1));
	        }
	        
			//Convert expression into Array of string
	        String[] expressionArr = new String[] { expression };
	        double leftVal = getNextOperand(expressionArr);
	        
	        //check expression length
	        expression = expressionArr[0];
	        if (expression.length() == 0) {
	            return leftVal;
	        }
	        
	        char operator = expression.charAt(0);
	        expression = expression.substring(1);

	        while (operator == '*' || operator == '/') {
	            expressionArr[0] = expression;
	            double rightVal = getNextOperand(expressionArr);
	            expression = expressionArr[0];
	            if (operator == '*') {
	                leftVal = leftVal * rightVal;
	            } else {
	                leftVal = leftVal / rightVal;
	            }
	            if (expression.length() > 0) {
	                operator = expression.charAt(0);
	                expression = expression.substring(1);
	            } else {
	                return leftVal;
	            }
	        }
	        
	        if (operator == '+') {
	            return leftVal + calc(expression);
	        } else {
	            return leftVal - calc(expression);
	        }
		}
        catch(Exception e) {
        	throw new Exception("Parse error, expression not proper");
        }
    }    
	
	/**
	 * This will give new operand
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
    private double getNextOperand(String[] exp) throws Exception{
        double res;
        if (exp[0].startsWith("(")) {
            int open = 1;
            int i = 1;
            while (open != 0) {
                if (exp[0].charAt(i) == '(') {
                    open++;
                } else if (exp[0].charAt(i) == ')') {
                    open--;
                }
                i++;
            }
            res = calc(exp[0].substring(1, i - 1));
            exp[0] = exp[0].substring(i);
        } else {
            int i = 1;
            if (exp[0].charAt(0) == '-') {
                i++;
            }
            while (exp[0].length() > i && isNumber((int) exp[0].charAt(i))) {
                i++;
            }
            res = Double.parseDouble(exp[0].substring(0, i));
            exp[0] = exp[0].substring(i);
        }
        return res;
    }

    
    /**
     * To check 
     * @param character
     * @return
     */
    private boolean isNumber(int character) {
        int zero = (int) '0';
        int nine = (int) '9';
        return (character >= zero && character <= nine) || character =='.';
    }
}
