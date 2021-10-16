package parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import parse.database.StatisticsService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import org.apache.logging.log4j.Logger;

@Entity(name = "Statistics")
@Table(name = "statistics", schema = "\"pagesDB\"")
public class Statistics implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="url")
    private String url;
    @Column(name="words")
    private String statisticsJSON;

    @Transient
    private String text;
    @Transient
    private TreeMap<String, Integer>  statistics;
    @Transient
    private Map<String, Integer> sortedStatistics;

    public Statistics() {}

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String u) {
        this.url = u;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String t) {
        this.text = t;
    }

    public String getStatisticsJSON() {
        return this.statisticsJSON;
    }

    /** counts words, sorts statistics by words' count */
    public void countWords() {
        this.text = this.text.toUpperCase().trim()
                .replaceAll("[,\\.—!?@\\(\\)\\[\\]\\+;:[0-9]\"«»[\s+\\-\\s+]/#&©”“]", " ");
        String[] words = this.text.split("\s+");

        this.statistics = new TreeMap<>();
        for(String word : words) {
            if(this.statistics.containsKey(word)) {
                this.statistics.put(word, this.statistics.get(word).intValue() + 1);
            }
            else {
                this.statistics.put(word, 1);
            }
        }

        this.sortedStatistics = StatisticsComparator.sortByValue(this.statistics);
        this.convertToJSON();
    }

    public void printStatistics() {
        for (Map.Entry s : this.sortedStatistics.entrySet()) {
            System.out.println(s.getValue() + " - " + s.getKey());
        }
    }

    public void saveToDatabase() {
        StatisticsService statisticsService = new StatisticsService();
        statisticsService.saveStatistics(this);
    }

    public void convertToJSON() {
        try {
            this.statisticsJSON = objectMapper.writeValueAsString(this.sortedStatistics);
        } catch (JsonProcessingException e) {
            Logger logger = LogManager.getLogger("Error converting to JSON.");
        }
    }
}
