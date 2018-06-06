package fr.esgi.tp;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;

/**
 * All information is stored using CSV. 
 * @author formation
 */
public class CsvLoader {
    /** the sql context to create datasets */
    private SQLContext context; 
    
    /**
     * Constructs a new empty loader with the given context 
     * @param context the context to load sql 
     */
    public CsvLoader(SQLContext context) {
        this.context = context; 
    }
    
    /**
     * Load a "cours" file, in CSV, to make a dataset for that context 
     * @param path the path of the file 
     * @return the dataset matching the file 
     */
    Dataset<Colonne> loadDatasetColonne(String path) {
        Dataset<Colonne> values = context.read()
                .option("header", true)
                .option("delimiter", ";")
                .option("dateFormat", "dd/MM/yy")
                .option("inferSchema", true)
                .csv(path)
                .as(Encoders.bean(Colonne.class));
        return values;
    }

        Dataset<Gramme3> loadDatasetGramme3(String path) {
        Dataset<Gramme3> values = context.read()
                .option("header", true)
                .option("delimiter", ";")
                .option("dateFormat", "dd/MM/yy")
                .option("inferSchema",true)
                .csv(path)
                .as(Encoders.bean(Gramme3.class));
        return values; 
    }
}
