package fr.esgi.tp;

import org.apache.log4j.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author formation
 */
public class LocalContexts {

    /**
     * the general configuration for tests : local and with two threads
     */
    private final static String CONTEXT_RESSOURCES = "local[2]";
    /**
     * spark configuration
     */
    private SparkConf configuration;
    /**
     * the JAVA spark context to create RDD
     */
    private JavaSparkContext context;
    /**
     * the spark session to build data frames
     */
    private SparkSession sqlSession;
    /**
     * unique instance (singleton)
     */
    private static LocalContexts instance = new LocalContexts();

    /**
     * Singleton hidden constructor
     */
    private LocalContexts() {
        // step 1 : force the hadoop dir 
        //System.setProperty("hadoop.home.dir", "D:\\Hadoop");
        // step 2 : make the objects
        configuration = new SparkConf()
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .setAppName("test ESGI").setMaster(CONTEXT_RESSOURCES);
        context = new JavaSparkContext(configuration);
        sqlSession = SparkSession.builder().config(configuration).getOrCreate();
        // step 3 : change logs level
        this.muteLogs();
    }

    /**
     * Get the unique instance the singleton manages 
     * @return a configured instance of the local context
     */
    public static synchronized LocalContexts get() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    /**
     * A java spark context creates JavaRDD. RDD is for Scala (roughly)
     * @return a new java spark context
     */
    public JavaSparkContext getContext() {
        return context;
    }

    /**
     * Get a spark session to make datasets 
     * @return a new spark session to make datasets
     */
    public SparkSession getSqlSession() {
        return sqlSession;
    }

    /**
     * Fake a financial datasource with a local file 
     * @return the path of the file to load cac40 data 
     */
    public String getGramme1() {
        return getClass().getClassLoader().getResource("test1.csv").getFile();
    }

    /**
     * Fake a financial datasource with a local file 
     * @return the path of the file for the SG data 
     */
    public String getGramme2() {
        return getClass().getClassLoader().getResource("test2.csv").getFile();
    }

    /**
     * Close the session and all the threads
     */
    public static synchronized void shutdown() {
        if (instance != null) {
            instance.sqlSession.stop();
            instance.context.close();
        }
        instance = null;
    }

    /**
     * Start (or restart) all the spark objects
     */
    public static synchronized void init() {
        shutdown();
        instance = new LocalContexts();
    }

    /**
     * Mute all the logs that come from the framework 
     * @return this, for chaining 
     */
    public LocalContexts muteLogs() {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("com").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        return this; 
    }
    
    /**
     * Activate all the logs that come from the framework 
     * @return this, for chaining 
     */
    public LocalContexts verboseLogs() {
        Logger.getLogger("org").setLevel(Level.DEBUG);
        Logger.getLogger("com").setLevel(Level.INFO);
        Logger.getLogger("akka").setLevel(Level.INFO);
        return this; 
    }
}
