/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.AbstractMap;
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
        Map<String,Integer> total = indx.initTotal();        
 
        
        
//TEST insertion


        for(int i=0 ; i<1 ; i++){
            Thread t1 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary,total));
            Thread t2 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary,total));
            Thread t3 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary,total));
            Thread t4 = new Thread(new indexer(linkStatus,counter,qr, (HashSet) dictionary,total));
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            
            
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
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
        
        System.out.println("start to save totals");
        file = new File("libs/total.txt");
        writer = new PrintWriter(file);
        for(Map.Entry<String,Integer> mp : total.entrySet())
        {
            writer.print(mp.getKey()+"   "+mp.getValue()+"\n");
        }
        

        
        writer.close();
        
        System.out.println("INDEXER FINISH");
        
        //------------------------------------------------------------------------------
        


//segment tree implementation in java
        
        
        
        //General TEST FOR GETCOUNT
//        List<String> links = new ArrayList<String>();
//        String original = "in";
//        int x=0;
//        links = indx.getLink(original,1);
//        for(String link :links){
//            x++;
//            System.out.println("link : "+link);
//            System.out.println(x + " numer of total words in Document is "+indx.getTotal(link));
//        }
//        Map<String,Integer> mp ;
//        mp = indx.getCount((ArrayList<String>) links,original,1);
//        for(Map.Entry<String,Integer> entry : mp.entrySet())
//        {
//                    System.out.println(" numer of counts in Document "+entry.getKey()+" is "+entry.getValue());
//        }


        
        
        
        
        
        

//
//TEST select link where stemmed_word or original_word =some_value
//        List<String> links = new ArrayList<String>();
//        links = indx.getLink("code",1);
//        for(String link :links){
//            System.out.println("link : "+link);
//
//            System.out.println("numer of total words in Document is "+indx.getTotal(link));
//        }


//TEST select total number of words in specfic link
        //System.out.println("numer of total words in Document is "+indx.getTotal("http://link.com"));



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
