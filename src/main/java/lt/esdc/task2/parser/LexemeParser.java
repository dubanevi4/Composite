package lt.esdc.task2.parser;

import lt.esdc.task2.composite.LeafTextComponent;
import lt.esdc.task2.composite.TextComponent;
import lt.esdc.task2.composite.TextComponentType;
import lt.esdc.task2.interpreter.ExpressionParser;

import java.util.regex.Pattern;

public class LexemeParser extends TextParser {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\d+([+\\-*/]\\d+)+");
    private final ExpressionParser expressionParser;

    public LexemeParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public TextComponent parse(String lexeme) {
        logger.info("Parsing lexeme: {}", lexeme);
        
        if (EXPRESSION_PATTERN.matcher(lexeme).matches()) {
            // Если лексема является арифметическим выражением
            double result = expressionParser.evaluate(lexeme);
            return new LeafTextComponent(String.valueOf(result), TextComponentType.EXPRESSION);
        } else {
            // Если лексема является обычным словом
            return new LeafTextComponent(lexeme, TextComponentType.WORD);
        }
    }
} 