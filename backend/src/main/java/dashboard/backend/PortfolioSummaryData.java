package dashboard.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioSummaryData {
  double totalValue;
  double totalGainLoss;
  double totalReturnPercent;
}
