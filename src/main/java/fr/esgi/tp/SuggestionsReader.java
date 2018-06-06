package fr.esgi.tp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author formation
 */
public class SuggestionsReader implements IRecommender {

    private String language ;
    private String path; 
    
    public SuggestionsReader(String path, String language) {
        this.language = language;
        this.path = getClass().getClassLoader().getResource(path).getFile();
    }

    @Override
    public String getLanguage() {
        return language; 
    }

    @Override
    public String[] recommend(String... words) {
        String[] key = words; 
        String[] result = null ; 
        try {
            result = this.find(key);
        } catch(Exception e) {
            throw new RuntimeException("Error while reading file : " + path , e);
        }
        return result; 
    }
    
    public String[] find(String[] keys) throws IOException {
        final String fileKey = key(keys);
        String[] result = null ; 
        // OK, find the key in the suggestions file 
        BufferedReader reader = new BufferedReader(new FileReader(this.path));
        boolean found = false ; 
        String line = reader.readLine(); 
        while(line != null && !found) {
            found = line.startsWith(fileKey);
            if (found) result = line.split(":")[1].split(",");
            else line = reader.readLine();
        }
        return result; 
    }
    
    private String key(String[] keys) {
        String result = keys[0];
        for(int i = 1 ; i < keys.length; i++) result = result + "," + keys[i] ;
        return result; 
    }
}
