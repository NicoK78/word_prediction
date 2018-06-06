package fr.esgi.tp;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;




public class Main {
    public static void main(String[] args) {
        /*final SQLContext context = LocalContexts.get().getSqlSession().sqlContext();
        final CsvLoader loader = new CsvLoader(context);
        Dataset<Cours> coursCAC40 = loader.loadDataset(LocalContexts.get().getCac40Path());
        Dataset<Cours> coursSG = loader.loadDataset(LocalContexts.get().getSGPath());
        JavaRDD<Double> clotureCAC40 = coursCAC40.javaRDD().map((Cours c) -> c.getCloture());
        JavaRDD<Double> clotureSG = coursSG.javaRDD().mapcf((Cours c) -> c.getCloture());
        double correlations = Statistics.corr(clotureSG, clotureCAC40);
        System.out.println(correlations);
        LocalContexts.shutdown();*/
        System.out.println("Bonjour");
        CorpusReader corpusReader = new CorpusReader();
        generateCSV(1,corpusReader);
        //generateCSV(2,corpusReader);
        /*SQLContext context = LocalContexts.get().getSqlSession().sqlContext();
        final CsvLoader loader = new CsvLoader(context);
        Dataset<Colonne> gramme1 = loader.loadDatasetColonne(LocalContexts.get().getGramme1());
        Dataset<Gramme3> gramme2 = loader.loadDatasetGramme3(LocalContexts.get().getGramme2());
        gramme1.createOrReplaceTempView("GRAMME1");
        gramme2.createOrReplaceTempView("GRAMME2");
        Dataset<Row> resultGramme1 = gramme1.sqlContext().sql("select  COL1 , COL2 , COUNT(COL1,COL2) from GRAMME1 group by COL1,COL2 order by COUNT(COL1,COL2) desc");
        Dataset<Row> resultGramme2 = gramme2.sqlContext().sql("select  COL1 , COL2 , COL3 , COUNT(COL1,COL2,COL3) from GRAMME2 group by COL1,COL2,COL3 order by COUNT(COL1,COL2,COL3) desc");

        resultGramme1.repartition(1).write().
                format("com.databricks.spark.csv").
                option("header", "true").
                option("delimiter", ",").
                save("retourSQL2");

        resultGramme2.repartition(1).write().
                format("com.databricks.spark.csv").
                option("header", "true").
                option("delimiter", ",").
                save("retourSQL3");

        System.out.println("SQL DONE !");*/
    }

     public static void generateCSV(int gramme , CorpusReader crp){
        int temp = 0;
         File file = new File("test" +gramme+".csv");
         try (FileOutputStream fop = new FileOutputStream(file)) {

             if (!file.exists()) {
                 file.createNewFile();
             }

             fop.write("COL1;COL2\n".getBytes());
             while(crp.hasNext()) {
                 byte[] contentInBytes = crp.next().getBytes();


                 if(temp == gramme + 1){
                     fop.write("\n".getBytes());
                     fop.write(contentInBytes);
                     fop.write(";".getBytes());
                     temp = 0;
                     temp++;
                 }else{
                     if(temp == gramme){
                         fop.write(contentInBytes);
                         temp++;
                     }else{
                         fop.write(contentInBytes);
                         fop.write(";".getBytes());
                         temp++;
                     }


                 }
                 byte[] tmp = contentInBytes;

             }
             fop.flush();
             fop.close();
             System.out.println("Done");

         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}