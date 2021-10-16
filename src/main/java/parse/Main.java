package parse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main (String[] args) {
        final Logger LOGGER = LogManager.getLogger("");
        System.out.print("URL: ");

        /** read URL */
        Statistics text = new Statistics();
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            text.setUrl(buffReader.readLine());
        } catch (IOException e) {
            LOGGER.error("Error reading URL. ", e);
        }

        /** get page text */
        try {
            Document doc = Jsoup.connect(text.getUrl()).get();
            text.setText(doc.text());

            text.countWords();
            text.printStatistics();
            text.saveToDatabase();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid URL.");
            LOGGER.error(e);
        } catch (IOException e) {
            System.out.println("Invalid URL.");
            LOGGER.error(e);
        }
    }
}
