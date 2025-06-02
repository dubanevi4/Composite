package lt.esdc.task2.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeTextComponent implements TextComponent {
    private final List<TextComponent> children;
    private final TextComponentType type;

    public CompositeTextComponent(TextComponentType type) {
        this.children = new ArrayList<>();
        this.type = type;
    }

    @Override
    public void add(TextComponent component) {
        children.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        children.remove(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public String getText() {
        StringBuilder result = new StringBuilder();
        for (TextComponent child : children) {
            result.append(child.getText());
            
            // Добавляем пробелы между компонентами в зависимости от типа
            if (type == TextComponentType.TEXT && child.getType() == TextComponentType.PARAGRAPH) {
                result.append("\n\n");
            } else if (type == TextComponentType.PARAGRAPH && child.getType() == TextComponentType.SENTENCE) {
                result.append(" ");
            } else if (type == TextComponentType.SENTENCE && child.getType() == TextComponentType.LEXEME) {
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

    @Override
    public TextComponentType getType() {
        return type;
    }
} 