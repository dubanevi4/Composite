package lt.esdc.task2;

import lt.esdc.task2.composite.TextComponent;
import lt.esdc.task2.composite.TextComponentType;
import lt.esdc.task2.parser.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TextProcessor {
    private static final Logger logger = LogManager.getLogger(TextProcessor.class);
    private final TextParser parser;

    public TextProcessor() {
        // Создаем цепочку парсеров
        TextParser lexemeParser = new LexemeParser();
        TextParser sentenceParser = new SentenceToLexemeParser();
        TextParser paragraphParser = new ParaToSentenceParser();
        TextParser textParser = new TextToParaParser();

        sentenceParser.setNextParser(lexemeParser);
        paragraphParser.setNextParser(sentenceParser);
        textParser.setNextParser(paragraphParser);

        this.parser = textParser;
    }

    public TextComponent parseText(String filePath) throws IOException {
        String text = Files.readString(Path.of(filePath));
        return parser.parse(text);
    }

    // 1. Сортировка абзацев по количеству предложений
    public List<TextComponent> sortParagraphsBySentenceCount(TextComponent text) {
        return text.getChildren().stream()
                .sorted(Comparator.comparingInt(p -> p.getChildren().size()))
                .collect(Collectors.toList());
    }

    // 2. Поиск предложений с самым длинным словом
    public List<TextComponent> findSentencesWithLongestWord(TextComponent text) {
        int maxWordLength = 0;
        List<TextComponent> result = new ArrayList<>();

        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                int currentMaxLength = sentence.getChildren().stream()
                        .filter(l -> l.getType() == TextComponentType.WORD)
                        .mapToInt(w -> w.getText().length())
                        .max()
                        .orElse(0);

                if (currentMaxLength > maxWordLength) {
                    maxWordLength = currentMaxLength;
                    result.clear();
                    result.add(sentence);
                } else if (currentMaxLength == maxWordLength) {
                    result.add(sentence);
                }
            }
        }
        return result;
    }

    // 3. Удаление предложений с числом слов меньше заданного
    public void removeSentencesWithFewerWords(TextComponent text, int minWordCount) {
        for (TextComponent paragraph : text.getChildren()) {
            paragraph.getChildren().removeIf(sentence -> 
                sentence.getChildren().stream()
                    .filter(l -> l.getType() == TextComponentType.WORD)
                    .count() < minWordCount
            );
        }
    }

    // 4. Поиск одинаковых слов и подсчет их количества
    public Map<String, Integer> countWords(TextComponent text) {
        Map<String, Integer> wordCount = new HashMap<>();
        
        text.getChildren().stream()
            .flatMap(p -> p.getChildren().stream())
            .flatMap(s -> s.getChildren().stream())
            .filter(l -> l.getType() == TextComponentType.WORD)
            .map(w -> w.getText().toLowerCase())
            .forEach(word -> wordCount.merge(word, 1, Integer::sum));
            
        return wordCount;
    }

    // 5. Подсчет гласных и согласных букв в предложении
    public Map<TextComponent, Map<String, Integer>> countVowelsAndConsonants(TextComponent text) {
        Map<TextComponent, Map<String, Integer>> result = new HashMap<>();
        String vowels = "аеёиоуыэюяaeiouy";
        
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                String sentenceText = sentence.getText().toLowerCase();
                
                int vowelCount = (int) sentenceText.chars()
                        .mapToObj(ch -> String.valueOf((char) ch))
                        .filter(vowels::contains)
                        .count();
                        
                int consonantCount = (int) sentenceText.chars()
                        .mapToObj(ch -> String.valueOf((char) ch))
                        .filter(ch -> ch.matches("[а-яa-z]") && !vowels.contains(ch))
                        .count();
                        
                Map<String, Integer> counts = new HashMap<>();
                counts.put("vowels", vowelCount);
                counts.put("consonants", consonantCount);
                
                result.put(sentence, counts);
            }
        }
        
        return result;
    }
} 