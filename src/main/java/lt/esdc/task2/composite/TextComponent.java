package lt.esdc.task2.composite;

import java.util.List;

public interface TextComponent {
    void add(TextComponent component);
    void remove(TextComponent component);
    List<TextComponent> getChildren();
    String getText();
    TextComponentType getType();
} 