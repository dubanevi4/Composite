package lt.esdc.task2.parser;

import lt.esdc.task2.composite.CompositeTextComponent;
import lt.esdc.task2.composite.TextComponent;
import lt.esdc.task2.composite.TextComponentType;

public class SentenceToLexemeParser extends TextParser {
    private static final String LEXEME_DELIMITER = "\\s+";

    @Override
    public TextComponent parse(String sentence) {
        logger.info("Parsing sentence into lexemes");
        CompositeTextComponent sentenceComposite = new CompositeTextComponent(TextComponentType.SENTENCE);
        
        String[] lexemes = sentence.split(LEXEME_DELIMITER);
        for (String lexeme : lexemes) {
            if (!lexeme.isEmpty()) {
                TextComponent lexemeComponent = nextParser.parse(lexeme);
                sentenceComposite.add(lexemeComponent);
            }
        }
        
        return sentenceComposite;
    }
} 