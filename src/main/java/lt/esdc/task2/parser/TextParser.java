package lt.esdc.task2.parser;

import lt.esdc.task2.composite.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TextParser {
    protected static final Logger logger = LogManager.getLogger(TextParser.class);
    protected TextParser nextParser;

    public void setNextParser(TextParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract TextComponent parse(String text);
} 