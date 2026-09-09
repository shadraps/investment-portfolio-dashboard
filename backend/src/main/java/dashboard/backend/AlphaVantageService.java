package dashboard.backend;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

@Service
public class AlphaVantageService {

  private final RestTemplate restTemplate;
  private final String apiKey;
  private long lastRequestTime;

  public AlphaVantageService() {
    this.restTemplate = new RestTemplate();
    this.apiKey = System.getenv("ALPHAVANTAGE_KEY");
    this.lastRequestTime = System.currentTimeMillis();
  }

  public double getCurrentPrice(String symbol) {
    String url =
        "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
            + symbol
            + "&apikey="
            + apiKey;

    waitForRateLimit();
    JsonNode root = restTemplate.getForObject(url, JsonNode.class);

    if (root.has("Error Message") || !root.has("Global Quote")) {
      throw new RuntimeException("Invalid ticker symbol or API error.");
    }

    String priceStr = root.get("Global Quote").get("05. price").asString();
    return Double.parseDouble(priceStr);
  }

  public AlphaVantageDailyResponse getDailySeries(String symbol) {
    String url =
        "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
            + symbol
            + "&apikey="
            + apiKey;

    waitForRateLimit();
    AlphaVantageDailyResponse response =
        restTemplate.getForObject(url, AlphaVantageDailyResponse.class);

    if (response == null || response.getTimeSeries() == null) {
      throw new RuntimeException("Could not fetch daily price series.");
    }

    return response;
  }

  private synchronized void waitForRateLimit() {
    long elapsed = System.currentTimeMillis() - lastRequestTime;
    long waitTime = 1500 - elapsed;

    if (waitTime > 0) {
      try {
        Thread.sleep(waitTime);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    lastRequestTime = System.currentTimeMillis();
  }
}
