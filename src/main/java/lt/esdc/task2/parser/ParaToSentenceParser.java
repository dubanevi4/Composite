package lt.esdc.task2.parser;

import lt.esdc.task2.composite.CompositeTextComponent;
import lt.esdc.task2.composite.TextComponent;
import lt.esdc.task2.composite.TextComponentType;

public class ParaToSentenceParser extends TextParser {
    private static final String SENTENCE_DELIMITER = "(?<=[.!?…])\\s+";

    @Override
    public TextComponent parse(String paragraph) {
        logger.info("Parsing paragraph into sentences");
        CompositeTextComponent paragraphComposite = new CompositeTextComponent(TextComponentType.PARAGRAPH);
        
        String[] sentences = paragraph.split(SENTENCE_DELIMITER);
        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty()) {
                TextComponent sentenceComponent = nextParser.parse(sentence.trim());
                paragraphComposite.add(sentenceComponent);
            }
        }
        
        return paragraphComposite;
    }
} 