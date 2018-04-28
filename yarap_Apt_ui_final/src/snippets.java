//package main;

import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class snippets {


    String getSnippet(String url, String query) throws IOException {
        Document document = Jsoup.connect(url).get();
        String description = document.select("meta[name=description]").get(0).attr("content");
//        System.out.println(description);
        Pattern pattern;
        Matcher matcher;
        String[] words = query.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String regex = "\\b" + words[i] + "\\b";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(description);
            if (matcher.find()) {
                return description;
            }
        }

        Elements paragraphs = document.select("p");
//        Element p = paragraphs.get(0);
//        String body = p.text();
        int kmax = 0;
        int max = 0;
        int max1 = 0;
        int max2 = 0;
        int max3 = 0;

        if(paragraphs.size() == 0)
            return "";

        for (int k = 0; k < paragraphs.size(); k++) {
            Element p = paragraphs.get(k);
            String body = p.text();

            List<Pair<Integer,Integer>> wordsindecies = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                String regex = "\\b" + words[i] + "\\b";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(body);
                while (matcher.find()) {
                    wordsindecies.add(new Pair<>(matcher.start(),matcher.end()));
                }
            }


            Collections.sort(wordsindecies, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {

                    return o1.getKey().compareTo(o2.getKey());
                }
            });

//            Collections.sort(wordsindecies);
            if (wordsindecies.size() == 1) {
                int tmax1 = wordsindecies.get(0).getKey();
                int tmax2 = Math.min(body.length(), tmax1 + 600);
                if(max == 0 && (tmax2 - tmax1) > ( max2 - max1))
                {
                    max1 = tmax1;
                    max2 = tmax2;
                    max3 = tmax2;
                    max = 0;
                    kmax = k;
                }

            }
            if (wordsindecies.size() >= 2) {
//                max1 = wordsindecies.get(0);
//                max2 = wordsindecies.get(1);
//                max = 1;
                for (int i = 0; i < wordsindecies.size() - 1; i++) {
                    for (int j = i + 1; j < wordsindecies.size(); j++) {
                        if (wordsindecies.get(i).getKey() - wordsindecies.get(j).getKey() <= 600) {
                            if (j - i > max) {
                                max = j - i;
                                max1 = wordsindecies.get(i).getKey();
                                max2 = wordsindecies.get(j).getKey();
                                max3 = wordsindecies.get(j).getValue();
                                kmax = k;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

        }


//        Elements paragraphs = document.select("p");
//        System.out.println(max);
        return paragraphs.get(kmax).text().substring(max1, Math.min(max1 + 600, paragraphs.get(kmax).text().length()));















//        List<Integer> wordsindecies = new ArrayList<>();
////        int[] wordsindecies;
//        String body = document.body().text();
//        for(int i =0;i<words.length;i++)
//        {
//            String regex = "\\b"+words[i]+"\\b";
//            pattern = Pattern.compile(regex);
//            matcher = pattern.matcher(body);
//            while (matcher.find())
//            {
//                wordsindecies.add(matcher.start());
//            }
//
//        }
//        Collections.sort(wordsindecies);
//        int max = 0;
//        int max1 = 0;
//        int max2 = 0;
//        if(wordsindecies.size() == 1)
//        {
//            max1 = wordsindecies.get(0);
//            max2 = Math.min(body.length(),max1+600);
//        }
//        if(wordsindecies.size()>=2)
//        {
////            max1 =wordsindecies.get(0);
////            max2 =wordsindecies.get(1);
////            max = 1;
//            for(int i=0;i<wordsindecies.size()-1;i++)
//            {
//                for(int j=i + 1;j<wordsindecies.size();j++)
//                {
//                    if(wordsindecies.get(i)-wordsindecies.get(j)<=600)
//                    {
//                        if(j-i>max)
//                        {
//                            max = j-i;
//                            max1 = wordsindecies.get(i);
//                            max2 = wordsindecies.get(j);
//                        }
//                    }
//                    else{
//                        break;
//                    }
//                }
//            }
//        }
//
//
////        Elements paragraphs = document.select("p");
//        return body.substring(max1,Math.min(max1+600,body.length()));













    }
}
