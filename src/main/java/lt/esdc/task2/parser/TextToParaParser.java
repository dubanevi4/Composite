package lt.esdc.task2.parser;

import lt.esdc.task2.composite.CompositeTextComponent;
import lt.esdc.task2.composite.TextComponent;
import lt.esdc.task2.composite.TextComponentType;

public class TextToParaParser extends TextParser {
    private static final String PARAGRAPH_DELIMITER = "(?m)^\\s+|(?<=\\n)\\s+";

    @Override
    public TextComponent parse(String text) {
        logger.info("Parsing text into paragraphs");
        CompositeTextComponent textComposite = new CompositeTextComponent(TextComponentType.TEXT);
        
        String[] paragraphs = text.split(PARAGRAPH_DELIMITER);
        for (String paragraph : paragraphs) {
            if (!paragraph.trim().isEmpty()) {
                TextComponent paragraphComponent = nextParser.parse(paragraph.trim());
                textComposite.add(paragraphComponent);
            }
        }
        
        return textComposite;
    }
} 