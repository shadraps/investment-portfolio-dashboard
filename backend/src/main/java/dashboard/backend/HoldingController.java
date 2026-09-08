package dashboard.backend;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HoldingController {
  private final HoldingService holdingService;
  private final AlphaVantageService alphaVantageService;

  public HoldingController(HoldingService holdingService, AlphaVantageService alphaVantageService) {
    this.holdingService = holdingService;
    this.alphaVantageService = alphaVantageService;
  }

  @PostMapping("/holdings")
  public Holding addHolding(@RequestBody Holding holding) {
    return holdingService.addHolding(holding);
  }

  @GetMapping("/holdings")
  public List<Holding> getAllHoldings() {
    return holdingService.getAllHoldings();
  }

  @DeleteMapping("/holdings/{id}")
  public void deleteHolding(@PathVariable Long id) {
    holdingService.deleteHolding(id);
  }

  @GetMapping("/stock/{symbol}")
  public double getLivePrice(@PathVariable String symbol) {
    return alphaVantageService.getCurrentPrice(symbol);
  }

  @GetMapping("/portfolio/summary")
  public List<HoldingSummaryDTO> getPortfolioSummary() {
    return holdingService.getPortfolioSummary();
  }
}
