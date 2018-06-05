package fr.esgi.tp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {

	private static HashMap<String, Integer> hm;
	
	public static void main(String[] args) throws IOException {
		
		OutputStream os = null;
		os = new FileOutputStream("/Users/nico/Documents/ESGI 2017_2018/BigData/esgi_201806-master/tp/src/main/resources/corpus_wordscount.csv");
		
		System.out.println("TEST");
		hm = new HashMap<>();
		CorpusReader cr = new CorpusReader("corpusABC.txt");
		while(cr.hasNext()) {
			String str = cr.next();
			System.out.println(str);
			if(!hm.containsKey(str)) {
				hm.put(str, 1);
			} else {
				hm.put(str, hm.get(str) + 1);
			}
		}
		
		for(Entry<String, Integer> s : hm.entrySet()) {
			String str = s.getKey() + ";" + s.getValue() + "\n";
			os.write(str.getBytes());
		}
		os.close();
		
		/*OutputStream os = null;
		os = new FileOutputStream("/Users/nico/Documents/ESGI 2017_2018/BigData/esgi_201806-master/tp/src/main/resources/corpusABC.txt");
		File file = new File("/Users/nico/Documents/ESGI 2017_2018/BigData/esgi_201806-master/tp/src/main/resources/corpus.txt");
		String charset = "ISO-8859-1"; // or what corresponds
		BufferedReader in = new BufferedReader(new InputStreamReader (new FileInputStream(file), charset));
		String line;
		while( (line = in.readLine()) != null) { 
			System.out.println(line);
			os.write(line.getBytes());
		}*/
	}

}
