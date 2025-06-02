package lt.esdc.task2.composite;

import java.util.Collections;
import java.util.List;

public class LeafTextComponent implements TextComponent {
    private final String text;
    private final TextComponentType type;

    public LeafTextComponent(String text, TextComponentType type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add to leaf component");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Cannot remove from leaf component");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public TextComponentType getType() {
        return type;
    }
} 