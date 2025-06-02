package lt.esdc.task2;

import lt.esdc.task2.composite.TextComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {
    private TextProcessor processor;
    private Path tempFile;
    
    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        processor = new TextProcessor();
        tempFile = tempDir.resolve("test.txt");
        String text = """
            \tFirst paragraph with a long word supercalifragilisticexpialidocious. Another sentence here.
            \tSecond paragraph. 2+2=4 is true.
            \tThird paragraph with three sentences. This is second. And this is third.
            """;
        Files.writeString(tempFile, text);
    }
    
    @Test
    void testSortParagraphsBySentenceCount() throws IOException {
        TextComponent text = processor.parseText(tempFile.toString());
        List<TextComponent> sorted = processor.sortParagraphsBySentenceCount(text);
        
        assertEquals(3, sorted.size());
        assertEquals(2, sorted.get(0).getChildren().size()); // Second paragraph
        assertEquals(2, sorted.get(1).getChildren().size()); // First paragraph
        assertEquals(3, sorted.get(2).getChildren().size()); // Third paragraph
    }
    
    @Test
    void testFindSentencesWithLongestWord() throws IOException {
        TextComponent text = processor.parseText(tempFile.toString());
        List<TextComponent> sentences = processor.findSentencesWithLongestWord(text);
        
        assertEquals(1, sentences.size());
        assertTrue(sentences.get(0).getText().contains("supercalifragilisticexpialidocious"));
    }
    
    @Test
    void testRemoveSentencesWithFewerWords() throws IOException {
        TextComponent text = processor.parseText(tempFile.toString());
        processor.removeSentencesWithFewerWords(text, 4);
        
        // Проверяем, что короткие предложения были удалены
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                assertTrue(sentence.getChildren().size() >= 4);
            }
        }
    }
    
    @Test
    void testCountWords() throws IOException {
        TextComponent text = processor.parseText(tempFile.toString());
        Map<String, Integer> wordCount = processor.countWords(text);
        
        assertTrue(wordCount.containsKey("this"));
        assertEquals(2, wordCount.get("this"));
        assertEquals(1, wordCount.get("supercalifragilisticexpialidocious"));
    }
    
    @Test
    void testCountVowelsAndConsonants() throws IOException {
        TextComponent text = processor.parseText(tempFile.toString());
        Map<TextComponent, Map<String, Integer>> counts = processor.countVowelsAndConsonants(text);
        
        assertFalse(counts.isEmpty());
        for (Map<String, Integer> sentenceCounts : counts.values()) {
            assertTrue(sentenceCounts.containsKey("vowels"));
            assertTrue(sentenceCounts.containsKey("consonants"));
            assertTrue(sentenceCounts.get("vowels") > 0);
            assertTrue(sentenceCounts.get("consonants") > 0);
        }
    }
} 