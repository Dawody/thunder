package thunder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class Popularity { // PageRank algorithm, score for edges between nodes
    static double InitialScore = 1;
    static double d = 0.85; //damping factor in score equation

    private class LinkNode {
        public long id;
        public String name;
        public long out_edges;
        public ArrayList<String> linked_nodes;
        public double init_score;
        public double first_score;
        public double second_score;
        public double third_score;

        LinkNode(long id, String name, long n) {
            this.name = name;
            this.id = id;
            this.out_edges = n;
            this.init_score = InitialScore;
            this.linked_nodes = new ArrayList<String>();
        }
        public void setFirst_score(double score, int n) { this.first_score =  this.init_score + score; }
        public void setSecond_score(double score, int n) { this.second_score = this.first_score + score; }
        public void setThird_score(double score, int n) { this.third_score = this.second_score + score; }
        public double getThird_score() { return this.third_score; }
        public double getSecond_score() { return this.second_score; }
        public double getFirst_score() { return this.first_score; }
        public double getInit_score() { return this.init_score; }
        public String getName() { return this.name; }
        public long getOut_edges() { return this.out_edges; }
        public void setLinked_nodes(String l) { this.linked_nodes.add(l); }
        public ArrayList<String> getLinked_nodes() { return this.linked_nodes; }
        public Long getId() { return this.id; }
    }

    private ArrayList<LinkNode> links = new ArrayList<LinkNode>();
    // link the pagerank with dbmaria
    public DBman db;

    Popularity()
    {
        db = new DBman();
    }

    public ArrayList<String> page_ranker() throws SQLException {
        //get data from tables of thunder
        String q1 = "SELECT * FROM links";
        ResultSet t_links = db.execute(q1);
        String x;
        LinkNode link;
        //handle the links edges among themselves
        while (t_links.next())
        {
            link = new LinkNode(t_links.getLong("id"),
                    t_links.getString("link"), t_links.getLong("total"));
            x=link.getName();
            String q2 = "SELECT follower FROM edges WHERE edges.followed='"+x+"'";
            ResultSet t_edges = db.execute(q2);
            while(t_edges.next())
            {
                link.setLinked_nodes(t_edges.getString("follower"));
            }
            links.add(link);
        }
        //2nd iteration to update the score of documents
        ArrayList<String> update_links;
        String link_name;
        double score;
        for(int i=0;i<links.size();i++) {
            score=0;
            update_links = links.get(i).getLinked_nodes();
            for (int j = 0; j < update_links.size(); j++) {
                link_name = update_links.get(j);
                for(int k = 0; k < links.size(); k++)
                {
                    if(links.get(k).getName().equals(link_name))
                    {
                        link = links.get(k);
                        score += (link.getInit_score()/link.getOut_edges());
                    }
                }
            }
            links.get(i).setFirst_score(score, links.size());
        }
        //3rd iteration for scoring
        for(int i=0;i<links.size();i++) {
            score=0;
            update_links = links.get(i).getLinked_nodes();
            for (int j = 0; j < update_links.size(); j++) {
                link_name = update_links.get(j);
                for(int k = 0; k < links.size(); k++)
                {
                    if(links.get(k).getName().equals(link_name))
                    {
                        link = links.get(k);
                        score += (link.getFirst_score()/link.getOut_edges());
                    }
                }
            }
            links.get(i).setSecond_score(score, links.size());
        }
        for(int i=0;i<links.size();i++) {
            score=0;
            update_links = links.get(i).getLinked_nodes();
            for (int j = 0; j < update_links.size(); j++) {
                link_name = update_links.get(j);
                for(int k = 0; k < links.size(); k++)
                {
                    if(links.get(k).getName().equals(link_name))
                    {
                        link = links.get(k);
                        score += (link.getSecond_score()/link.getOut_edges());
                    }
                }
            }
            links.get(i).setThird_score(score, links.size());
        }
        //sort arraylist of links by score
        links.sort(Comparator.comparingDouble(LinkNode::getThird_score));
        ArrayList<String> sorted_links = new ArrayList<String>();
        for(int i=links.size()-1;i>=0;i--)
        {
            /*System.out.println(links.get(i).getName()
                    +" "+links.get(i).getFirst_score()
                    +" "+links.get(i).getSecond_score()
                    +" "+links.get(i).getThird_score()
            );*/
            sorted_links.add(links.get(i).getName());
        }
        return sorted_links;
    }
}
