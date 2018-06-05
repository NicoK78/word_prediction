package fr.esgi.tp;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author formation
 */
public class TestCorpusReader {

    @Test
    public void testReader() {
        try (CorpusReader reader = new CorpusReader()) {
            for (String s : reader) {
                Assert.assertTrue(s != null);
                Assert.assertTrue(s.length() >= 1);
                //System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
