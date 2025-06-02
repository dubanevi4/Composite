package lt.esdc.task2.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class ExpressionParser {
    private final Map<String, BinaryOperator<Double>> operators;

    public ExpressionParser() {
        operators = new HashMap<>();
        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> a / b);
    }

    public double evaluate(String expression) {
        Deque<Double> numbers = new ArrayDeque<>();
        Deque<String> ops = new ArrayDeque<>();
        
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        
        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;
            
            if (operators.containsKey(token)) {
                while (!ops.isEmpty() && hasPrecedence(token, ops.peek())) {
                    applyOperation(numbers, ops.pop());
                }
                ops.push(token);
            } else {
                numbers.push(Double.parseDouble(token));
            }
        }
        
        while (!ops.isEmpty()) {
            applyOperation(numbers, ops.pop());
        }
        
        return numbers.pop();
    }
    
    private boolean hasPrecedence(String op1, String op2) {
        return (op2.equals("*") || op2.equals("/")) && (op1.equals("+") || op1.equals("-"));
    }
    
    private void applyOperation(Deque<Double> numbers, String op) {
        double b = numbers.pop();
        double a = numbers.pop();
        numbers.push(operators.get(op).apply(a, b));
    }
} 