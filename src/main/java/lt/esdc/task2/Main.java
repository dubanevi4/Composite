package lt.esdc.task2;

import lt.esdc.task2.composite.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Require to add source text file as argument, e.g. src/main/resources/sample.txt");
            return;
        }

        try {
            TextProcessor processor = new TextProcessor();
            TextComponent text = processor.parseText(args[0]);
            
            // 1. Сортировка абзацев по количеству предложений
            System.out.println("\nСортировка абзацев по количеству предложений:");
            List<TextComponent> sortedParagraphs = processor.sortParagraphsBySentenceCount(text);
            sortedParagraphs.forEach(p -> System.out.println(p.getText()));

            // 2. Поиск предложений с самым длинным словом
            System.out.println("\nПредложения с самым длинным словом:");
            List<TextComponent> sentencesWithLongestWord = processor.findSentencesWithLongestWord(text);
            sentencesWithLongestWord.forEach(s -> System.out.println(s.getText()));

            // 3. Удаление предложений с числом слов меньше 5
            System.out.println("\nТекст после удаления коротких предложений (меньше 5 слов):");
            processor.removeSentencesWithFewerWords(text, 5);
            System.out.println(text.getText());

            // 4. Поиск одинаковых слов
            System.out.println("\nСтатистика повторяющихся слов:");
            Map<String, Integer> wordCount = processor.countWords(text);
            wordCount.entrySet().stream()
                    .filter(e -> e.getValue() > 1)
                    .forEach(e -> System.out.printf("%s: %d раз(а)%n", e.getKey(), e.getValue()));

            // 5. Подсчет гласных и согласных
            System.out.println("\nСтатистика по гласным и согласным в предложениях:");
            Map<TextComponent, Map<String, Integer>> letterStats = processor.countVowelsAndConsonants(text);
            letterStats.forEach((sentence, stats) -> {
                System.out.println("\nПредложение: " + sentence.getText());
                System.out.printf("Гласных: %d, Согласных: %d%n",
                        stats.get("vowels"),
                        stats.get("consonants"));
            });

        } catch (IOException e) {
            logger.error("Error processing file: " + e.getMessage(), e);
        }
    }
} 