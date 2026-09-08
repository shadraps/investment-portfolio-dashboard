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

  public List<Holding> getAllHoldings() {
    return holdingRepository.findAll();
  }

  public void deleteHolding(Long id) {
    holdingRepository.deleteById(id);
  }

  public List<HoldingSummaryDTO> getPortfolioSummary() {
    return holdingRepository.findAll().stream().map(this::calculateSummary).toList();
  }

  private HoldingSummaryDTO calculateSummary(Holding holding) {
    double currentPrice = alphaVantageService.getCurrentPrice(holding.getSymbol());
    double shares = holding.getShares();
    double costBasis = holding.getAvgCostBasis();

    double currentValue = currentPrice * shares;
    double initialValue = costBasis * shares;
    double gainLoss = currentValue - initialValue;
    double percentReturn = (gainLoss / initialValue) * 100;

    return new HoldingSummaryDTO(
        holding.getId(),
        holding.getSymbol(),
        shares,
        costBasis,
        currentPrice,
        currentValue,
        gainLoss,
        percentReturn);
  }
}
