package thunder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

public class BasicWebCrawler {


    public static void main(String[] args) {
        //1. Pick a URL from the frontier
//        DBman dbman = new DBman();
        Set<String> links = new ConcurrentSkipListSet<String>();
        BlockingQueue<String> urls = new LinkedBlockingQueue<String>();
        urls.add("https://www.geeksforgeeks.org/");
        Runnable CrawlerTask = new crawler(links,urls);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for(int i=0;i<50;i++)
        {
            executorService.submit(CrawlerTask);
        }

    }

}

class crawler implements Runnable {
    private Set<String> links;
    private BlockingQueue<String> urls;
    AtomicInteger atomicInteger = new AtomicInteger(0);
    public crawler(Set<String> l, BlockingQueue<String> u)
    {
        links = l;
        urls = u;
    }
    @Override
    public void run() {

        String URL;
        System.out.println(Thread.currentThread().getName());
        while(true)
        {
            if (!urls.isEmpty()) {
                URL = urls.remove();
//            System.out.println(URL);
                if (!links.contains(URL)) {
                    try {
                        //4. (i) If not add it to the index
                        if (links.add(URL)) {
                            System.out.print(Thread.currentThread().getName()+"  ");
                            System.out.print(atomicInteger.get());
                            System.out.print("  ");
                            System.out.println(URL);

                        }

                        //2. Fetch the HTML code
                        Document document = Jsoup.connect(URL).get();
//                        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
                        BufferedWriter writer = new BufferedWriter(new FileWriter("documents/" +atomicInteger.incrementAndGet()+".html"));
                        writer.write(URL);
                        writer.newLine();
                        writer.write(document.toString());

//                        writer.close();
                        //3. Parse the HTML to extract links to other URLs
                        Elements linksOnPage = document.select("a[href]");

                        //5. For each extracted URL... go back to Step 4.
                        for (Element page : linksOnPage) {
//                        System.out.println(page.attr("abs:href"));
                            urls.add(page.attr("abs:href"));
//                        getPageLinks(page.attr("abs:href"));
                        }
                    } catch (IOException e) {
                        System.err.println("For '" + URL + "': " + e.getMessage());
                    }
                }
            }
        }

    }
}