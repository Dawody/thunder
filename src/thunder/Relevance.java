package thunder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

//I will use stemmer(String word)-> to stem query words,
// getLink(String word,int type)-> to get list of links that have this word,
// getTotal(String link)-> to get total number of words in the document,
// getCount(String link , String word , int type)-> to get number of occurrences in the document

public class Relevance { // TF-IDF score for keywords in query found in the document
    private indexer ind;

    Relevance()
    {
        ind = new indexer();
    }
    public Map<String, Double> tfidf(String[] query){ //tf() * idf() for each document

        ArrayList<String> word_links = null, stem_links = null;
        ArrayList<Double> tf_original = new ArrayList<Double>();
        ArrayList<Double> tf_stemmed =  new ArrayList<Double>();
        ArrayList<Integer> org_links_count = new ArrayList<Integer>();
        ArrayList<Integer> stm_links_count = new ArrayList<Integer>();
        String stemmed_word;
        ArrayList<String> stem_words = new ArrayList<String>();
        Map<String, ArrayList<String>> original_urls = null;
        Map<String, ArrayList<String>> stemmed_urls = null;

        //tf part
        for(int j = 0; j < query.length; j++)
        {
            stemmed_word = ind.stemmer(query[j]);
            stem_words.add(stemmed_word);
            word_links = ind.getLink(query[j], 1);
            original_urls.put(query[j],word_links);
            org_links_count.add(word_links.size());
            stem_links = ind.getLink(stemmed_word, 0);
            stemmed_urls.put(stemmed_word,stem_links);
            stm_links_count.add(stem_links.size());

            for(int i = 0; i < word_links.size(); i++)
            {
                double total = ind.getTotal(word_links.get(i));
                double count= ind.getCount(word_links.get(i),query[j],1);
                double tf = count/total;
                tf_original.add(tf);
            }
            for(int i = 0; i < stem_links.size(); i++)
            {
                double total = ind.getTotal(stem_links.get(i));
                double count= ind.getCount(stem_links.get(i),stemmed_word,0);
                double tf = count/total;
                tf_stemmed.add(tf);
            }
        }
        //idf part
        Double org_links_total = 0.0, stm_links_total = 0.0;
        ArrayList<Double> idf_original = new ArrayList<Double>();
        ArrayList<Double> idf_stemmed = new ArrayList<Double>();
        for(int i = 0; i < org_links_count.size(); i++)
        {
            org_links_total += org_links_count.get(i);
        }
        for(int i = 0; i < stm_links_count.size(); i++)
        {
            stm_links_total += stm_links_count.get(i);
        }
        for(int i = 0; i < org_links_count.size(); i++)
        {
            idf_original.add(Math.log(org_links_total / (double)org_links_count.get(i)));
        }
        for(int i = 0; i < stm_links_count.size(); i++)
        {
            idf_stemmed.add(Math.log(stm_links_total / (double)stm_links_count.get(i)));
        }
        //tf-idf part
        Map<String,Double> tf_idf = null;
        int k = 0;
        for(int i = 0; i < original_urls.keySet().size(); i++)
        {
            for (int j = 0; j < original_urls.get(query[i]).size(); j++,k++)
            {
                tf_idf.put(original_urls.get(query[i]).get(j),
                        tf_idf.get(original_urls.get(query[i]).get(j))+
                                tf_original.get(k)*idf_original.get(k) );
            }
        }
        for(int i = 0; i < stemmed_urls.keySet().size(); i++)
        {
            for (int j = 0; j < stemmed_urls.get(stem_words.get(i)).size(); j++,k++)
            {
                tf_idf.put(stemmed_urls.get(stem_words.get(i)).get(j),
                        tf_idf.get(stemmed_urls.get(stem_words.get(i)).get(j))+
                                tf_stemmed.get(k)*idf_stemmed.get(k) );
            }
        }
        return tf_idf;
    }
}
