/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author dawod
 */
public class Thunder {
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        indexer indx = new indexer();
        Map<Integer,Boolean> linkStatus = new HashMap<Integer,Boolean>();
        linkStatus = indx.getLinkStatus();
        Integer counter = indx.initFileCounter();
        Query qr = indx.initQuery();
        Set dictionary = indx.initDictionary();
        
        
//TEST insertion


        for(int i=0 ; i<1 ; i++){
            //indx.indexFiles();
            Thread t1 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary));
            Thread t2 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary));
            Thread t3 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary));
            Thread t4 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary));
//            Thread t5 = new Thread(new indexer(linkStatus,counter,qr));
//            Thread t6 = new Thread(new indexer(linkStatus,counter,qr));
//            Thread t7 = new Thread(new indexer(linkStatus,counter,qr));
//            Thread t8 = new Thread(new indexer(linkStatus,counter,qr));
//            Thread t9 = new Thread(new indexer(linkStatus,counter,qr));
//            Thread t10 = new Thread(new indexer(linkStatus,counter,qr));
            t1.start();
            t2.start();
            t3.start();
            t4.start();
//            t5.start();
//            t6.start();
//            t7.start();
//            t8.start();
//            t9.start();
//            t10.start();
            
            
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
//                t5.join();
//                t6.join();
//                t7.join();
//                t8.join();
//                t9.join();
//                t10.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Thunder.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        System.out.println("indexing finish..");
        ArrayList<Integer> linkIds = new ArrayList<Integer>();
        for (Map.Entry<Integer, Boolean> entry : linkStatus.entrySet())
        {
            if(!entry.getValue())
            {
                linkIds.add(entry.getKey());
            }
                
                            
        }
        System.out.println("start to update link status..");
        indx.updateLinkStatus(linkIds);
        System.out.println("start to save the dictionary");
        

        File file = new File("libs/dictionary.txt");
        PrintWriter writer = new PrintWriter(file);
        for(Object str : dictionary)
        {
            writer.print(str.toString()+"\n");
        }
        writer.close();
        
        System.out.println("INDEXER FINISH");
        
        //------------------------------------------------------------------------------
        




//
//TEST select link where stemmed_word or original_word =some_value
//        List<String> links = new ArrayList<String>();
//        links = indx.getLink("sucessed",1);
//        for(String link :links){
//            System.out.println("link : "+link);
//        }


//TEST select total number of words in specfic link
//        System.out.println("numer of total words in Document is "+indx.getTotal("http://link.com"));



//
//TEST select tag where link =some_link and stemmed_word or original_word =some_value
//        List<String> tags = new ArrayList<String>();
//        tags = indx.getTags("http://link.com","sucessed",1);
//        for(String tag :tags){
//            System.out.println("tag : "+tag);
//        }


//TEST select number of repeatance of stem or original word in specific link
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","anim",0));
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","animal",1));
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","animation",1));
//    







//TEST the stemmer and discovered bugs!
//System.out.println(indx.stemmer("animation"));
//System.out.println(indx.stemmer("animal"));
//System.out.println(indx.stemmer("mouse"));
//System.out.println(indx.stemmer("mice"));


    }
    
}
