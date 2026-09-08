package dashboard.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HoldingSummaryDTO {

  private Long id;
  private String symbol;
  private Double shares;
  private Double avgCostBasis;

  private Double currentPrice;
  private Double currentValue;
  private Double gainLoss;
  private Double percentReturn;
}
