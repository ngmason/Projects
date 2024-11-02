package edu.ser222.m04_02;

/**
 * Completes interface methods from KanjiMain and runs Main method.
 *
 * Completion time: 4 hours
 *
 * @author Nina Mason, Acuna, Buckner
 * @version 4/27/2024
 */

//Note: not all of these packages may be needed.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;

public class CompletedMain implements KanjiMain {

    //Do not add any member variables to this class.

    //TODO: implement interface methods.

    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph) 
    {
    	  HashMap<Integer, String> kanjiMap = new HashMap<>();
          try {
        	  BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), "UTF8"));
              String line;
              reader.readLine();
              while ((line = reader.readLine()) != null) {
            	  if (line.startsWith("#")) continue;
                  String[] parts = line.split("\t");
                  int id = Integer.parseInt(parts[0]);
                  String character = parts[1];
                  kanjiMap.put(id, character);
                  graph.addVertex(id);
              }
              reader.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return kanjiMap;
    }

    public void loadDataComponents(String filename, EditableDiGraph graph) {
    	 try {
    		 BufferedReader reader = new BufferedReader(new FileReader(filename));
             String line;
             while ((line = reader.readLine()) != null) {
            	 if (line.startsWith("#")) continue;
                 String[] parts = line.split("\t");
                 int from = Integer.parseInt(parts[0]);
                 int to = Integer.parseInt(parts[1]);
                 graph.addEdge(from, to);
             }
             reader.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort, HashMap<Integer, String> kanjiMap) {
    	  
    	if (!topSort.isDAG()) {
            return "The graph contains a cycle and cannot be topologically sorted.";
        }
    	String result = "";
        for (int id : topSort.order()) {
            result += kanjiMap.get(id);
        }
        return result;
    }

    public static void main(String[] args) {
        /***************************************************************************
         * START - CORE DRIVER LOGIC, DO NOT MODIFY                                *
         **************************************************************************/
        String FILENAME_KANJI = "C:\\Users\\nina1\\Downloads\\LearningKanjiProject\\src\\edu\\ser222\\m04_02\\data-kanji.txt";
        String FILENAME_COMPONENTS = "C:\\Users\\nina1\\Downloads\\LearningKanjiProject\\src\\edu\\ser222\\m04_02\\data-components.txt";

        KanjiMain driver = new CompletedMain();
        EditableDiGraph graph = new BetterDiGraph();

        HashMap<Integer, String> kanjiMap = driver.loadKanji(FILENAME_KANJI, graph);
        driver.loadDataComponents(FILENAME_COMPONENTS, graph);

        TopologicalSort intuitive = new IntuitiveTopological(graph);

        System.out.println(driver.buildOrderString(graph, intuitive, kanjiMap));

        /***************************************************************************
         * END - CORE DRIVER LOGIC, DO NOT MODIFY                                  *
         **************************************************************************/

        //NOTE: feel free to temporarily comment out parts of the above code while
        //you incrementally develop your program. Just make sure all of it is there
        //when you test the final version of your program.

        //OPTIONAL: add code for extra credit here.
    }
}