package thunder;

import java.util.*;
import java.util.Map.Entry;

//I will use stemmer(String word)-> to stem query words,
// getLink(String word,int type)-> to get list of links that have this word,
// getTotal(String link)-> to get total number of words in the document,
// getCount(String link , String word , int type)-> to get number of occurrences in the document

public class Relevance { // TF-IDF score for keywords in query found in the document
    private indexer ind;
    private DBman db;

    Relevance()
    {
        ind = new indexer();
        db = new DBman();
    }
    public ArrayList<String> decide(Query_obj qb)
    {
        ArrayList<String> o = new ArrayList<String>();
        int x = qb.getPh();
        saveQueries(qb.getQuery());
        switch (x)
        {
            case 0:
                o = tfidf(qb.getQuery_words());
                break;
            case 1:
                o = phraseSearch(qb.getQuery_words());
                break;
        }
        return o;
    }
    private class phraseObj {
        private String word;
        private ArrayList<String> links = new ArrayList<String>();
        private ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();

        private phraseObj(String w) { this.word = w; }
        public void setLinks(ArrayList<String> links) { this.links = links; }
        public void setPositions(ArrayList<Integer> positions) { this.positions.add(positions); }
        public ArrayList<ArrayList<Integer>> getPositions() { return positions; }
        public ArrayList<String> getLinks() { return links; }
        public ArrayList<Integer> getPosition(int i) { return positions.get(i); }
        public String getLink(int i) { return links.get(i); }
        public String getWord() { return word; }
    }

    private class TfidfObj {
        private String org_word;
        private String stm_word = "";
        private ArrayList<String> org_links = new ArrayList<String>();
        private ArrayList<String> stm_links = new ArrayList<String>();
        private ArrayList<Double> org_links_tf = new ArrayList<Double>();
        private ArrayList<Double> stm_links_tf  = new ArrayList<Double>();
        private double org_idf = 0.0;
        private double stm_idf = 0.0;

        private TfidfObj(String s) { this.org_word = s; }
        public void setOrg_idf(double org_idf) { this.org_idf = org_idf; }
        public void setStm_idf(double stm_idf) { this.stm_idf = stm_idf; }
        public void setOrg_links(ArrayList<String> org_links) { this.org_links = org_links; }
        public void setStm_links(ArrayList<String> stm_links) { this.stm_links = stm_links; }
        public void setStm_word(String stm_word) { this.stm_word = stm_word; }
        public void setOrg_link_tf(double org_link_tf) { this.org_links_tf.add(org_link_tf); }
        public void setStm_link_tf(double stm_link_tf) { this.stm_links_tf.add(stm_link_tf); }
        public ArrayList<Double> getOrg_links_tf() { return org_links_tf; }
        public ArrayList<Double> getStm_links_tf() { return stm_links_tf; }
        public double getOrg_idf() { return org_idf; }
        public double getStm_idf() { return stm_idf; }
        public ArrayList<String> getOrg_links() { return org_links; }
        public ArrayList<String> getStm_links() { return stm_links; }
        public String getOrg_word() { return org_word; }
        public String getStm_word() { return stm_word; }
    }

    private void saveQueries(String q) {
        //save queries in db in table queries
        String query ="INSERT IGNORE INTO queries (query) VALUES ('"+q+"')";
        db.execute(query);
    }

    private ArrayList<String> phraseSearch(String[] query) {
        for(int j = 0; j < query.length; j++) {
            phraseObj o = new phraseObj(query[j]);
            o.setLinks(ind.getLink(query[j], 1));
            for(int i = 0;i<o.getLinks().size();i++) {
                o.setPositions(ind.getPositions(o.getLink(i),query[j],1));
            }
        }
        return null;
    }

    private ArrayList<String> tfidf(String[] query){ //tf() * idf() for each document
        ArrayList<TfidfObj> data = new ArrayList<TfidfObj>();
        ArrayList<String> output = new ArrayList<String>();
        int[] a={0,0};
        //----------------------------------------------------------------------------------
        //tf part
        a = tf(query, data, a);
        //idf part
        idf(data, a);
        //tf-idf part
        tf_idf(data, output);
        return output;
    }
    private int[] tf(String[] query, ArrayList<TfidfObj> d, int[] arr) {
        for(int j = 0; j < query.length; j++)
        {
            TfidfObj o = new TfidfObj(query[j]);
            o.setStm_word(ind.stemmer(query[j]));
            o.setOrg_links(ind.getLink(query[j],1));
            o.setStm_links(ind.getLink(o.getStm_word(),0));
            arr[0] += o.getOrg_links().size();
            arr[1] += o.getStm_links().size();

            for(int i = 0; i < o.getOrg_links().size(); i++)
            {
                double total = ind.getTotal(o.getOrg_links().get(i));
                double count= ind.getCount(o.getOrg_links().get(i),query[j],1);
                double tf = count/total*1000;
                o.setOrg_link_tf(tf);
            }
            for(int i = 0; i < o.getStm_links().size(); i++)
            {
                double total = ind.getTotal(o.getStm_links().get(i));
                double count= ind.getCount(o.getStm_links().get(i),o.getStm_word(),0);
                double tf = count/total*1000;
                o.setStm_link_tf(tf);
            }
            d.add(o);
        }
        return arr;
    }

    private void idf(ArrayList<TfidfObj> d, int[] arr) {
        for(int i = 0; i < d.size(); i++) {
            d.get(i).setOrg_idf(Math.log((double)arr[0] /
                    (double)d.get(i).getOrg_links().size()));
            d.get(i).setStm_idf(Math.log((double)arr[1] /
                    (double)d.get(i).getStm_links().size()));
        }
    }

    private void tf_idf(ArrayList<TfidfObj> d, ArrayList<String> outp) {
        Map<String, Double> set_outp = new HashMap<String, Double>();
        TfidfObj ob1;
        ArrayList<String> ob2,ob3;
        for (int i = 0; i < d.size(); i++) {
            ob1 = d.get(i);
            ob2 = ob1.getOrg_links();
            ob3 = ob1.getStm_links();
            for (int j = 0; j < ob2.size(); j++) {
                if (!set_outp.containsKey(ob2.get(j))) {
                    set_outp.put(ob2.get(j), ob1.getOrg_links_tf().get(j) * ob1.getOrg_idf()); }
                else {
                    set_outp.put(ob2.get(j), ob1.getOrg_links_tf().get(j) * ob1.getOrg_idf()
                        + set_outp.get(ob2.get(j))); }
            }
            for (int k = 0; k < ob3.size(); k++) {
                if (!set_outp.containsKey(ob3.get(k))){
                    set_outp.put(ob3.get(k), ob1.getStm_links_tf().get(k) * ob1.getStm_idf()*0.1); }
                else {
                    set_outp.put(ob3.get(k), ob1.getStm_links_tf().get(k) * ob1.getStm_idf()*0.1
                            + set_outp.get(ob3.get(k))); }
            }
        }

        Set<Entry<String, Double>> set = set_outp.entrySet();
        List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(
                set);
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(int i = 0; i<list.size();i++) {
            System.out.println(list.get(i).getKey()+" "+list.get(i).getValue());
            outp.add(list.get(i).getKey());
        }
    }
}
