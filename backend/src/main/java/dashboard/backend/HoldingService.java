package dashboard.backend;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HoldingService {
  private final HoldingRepository holdingRepository;
  private final AlphaVantageService alphaVantageService;

  public HoldingService(
      HoldingRepository holdingRepository, AlphaVantageService alphaVantageService) {
    this.holdingRepository = holdingRepository;
    this.alphaVantageService = alphaVantageService;
  }

  public Holding addHolding(Holding holding) {
    return holdingRepository.save(holding);
  }

  public List<HoldingDTO> getAllHoldings() {
    return holdingRepository.findAll().stream().map(this::calculateHoldingDTO).toList();
  }

  public void deleteHolding(Long id) {
    holdingRepository.deleteById(id);
  }

  public PortfolioSummaryData getPortfolioSummary() {
    return calculatePortfolioSummaryData();
  }

  private HoldingDTO calculateHoldingDTO(Holding holding) {
    double currentPrice = alphaVantageService.getCurrentPrice(holding.getSymbol());
    double shares = holding.getShares();
    double avgCostBasis = holding.getAvgCostBasis();

    double currentValue = currentPrice * shares;
    double initialValue = avgCostBasis * shares;
    double gainLoss = currentValue - initialValue;
    double percentReturn = (gainLoss / initialValue) * 100;

    return new HoldingDTO(
        holding.getId(),
        holding.getSymbol(),
        shares,
        avgCostBasis,
        currentPrice,
        currentValue,
        gainLoss,
        percentReturn);
  }

  private PortfolioSummaryData calculatePortfolioSummaryData() {
    List<HoldingDTO> holdings = getAllHoldings();
    double totalValue = 0;
    double totalGainLoss = 0;
    double totalInitialValues = 0;

    for (HoldingDTO holding : holdings) {
      totalValue += holding.getCurrentValue();
      totalGainLoss += holding.getGainLoss();
      totalInitialValues += holding.getAvgCostBasis() * holding.getShares();
    }

    double totalReturnPercent = (totalGainLoss / totalInitialValues) * 100;

    return new PortfolioSummaryData(totalValue, totalGainLoss, totalReturnPercent);
  }
}
