package parse.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parse.Statistics;

public class StatisticsService {
    private final Logger LOGGER = LogManager.getLogger("");
    private static StatisticsDAO statisticsDAO;

    public StatisticsService() {
        statisticsDAO = new StatisticsDAO();
    }

    public void saveStatistics(Statistics s) {
        try {
            statisticsDAO.save(s);
            System.out.println("Data's been saved to DB.");
        } catch (Exception e) {
            System.out.println("Error persisting data.");
            LOGGER.error("Error persisting data.", e);
        }
    }
}
