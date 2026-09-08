package dashboard.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;

@Data
public class AlphaVantageDailyResponse {

  @JsonProperty("Time Series (Daily)")
  private Map<String, DailyPrice> timeSeries;

  @Data
  public static class DailyPrice {
    @JsonProperty("4. close")
    private String close;
  }
}
