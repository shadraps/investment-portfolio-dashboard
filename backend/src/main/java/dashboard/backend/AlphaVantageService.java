package dashboard.backend;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

@Service
public class AlphaVantageService {

  private final RestTemplate restTemplate;
  private final String apiKey;

  public AlphaVantageService() {
    this.restTemplate = new RestTemplate();
    this.apiKey = System.getenv("ALPHAVANTAGE_KEY");
  }

  public double getCurrentPrice(String symbol) {
    String url =
        "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
            + symbol
            + "&apikey="
            + apiKey;

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

    AlphaVantageDailyResponse response =
        restTemplate.getForObject(url, AlphaVantageDailyResponse.class);

    if (response == null || response.getTimeSeries() == null) {
      throw new RuntimeException("Could not fetch daily price series.");
    }

    return response;
  }
}
