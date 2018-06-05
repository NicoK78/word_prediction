package fr.esgi.tp;

/**
 * Implementation of the recommender for the French language
 * @author formation
 */
public class FrenchRecommender implements IRecommender {

    @Override
    public String getLanguage() {
        return "French";
    }

    @Override
    public String[] recommend(String... words) {
        return new String[]{"je","tu","mon","ma"};
    }
    
}
