package fr.esgi.tp;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test the french recommender 
 * @author formation
 */
public class TestFrenchRecommender {
    
    private final IRecommender recommender = new SuggestionsReader("suggestions.txt","French");
    
    @BeforeClass
    public static void init() {
        System.setProperty("file.encoding", "UTF-8");
    }
    
    @Test
    public void testLanguage() {
        assertEquals("French",recommender.getLanguage());
    }
    
    @Test
    public void testACause() {
        String[] suggestions = recommender.recommend("à","cause");
        assertTrue(suggestions != null && suggestions.length >= 1);
        boolean containsDE = false; 
        for(int i = 0; i < suggestions.length && !containsDE; i++) {
            containsDE = suggestions[i].trim().equalsIgnoreCase("de");
        }
        assertTrue(containsDE);
    }
    
    @Test
    public void testJe() {
        String[] suggestions = recommender.recommend("je");
        assertTrue(suggestions != null && suggestions.length >= 1);
        boolean containsDE = false; 
        for(int i = 0; i < suggestions.length && !containsDE; i++) {
            containsDE = suggestions[i].trim().equalsIgnoreCase("suis");
        }
        assertTrue(containsDE);
    }
}
