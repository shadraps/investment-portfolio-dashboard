import type { PortfolioSummaryData } from "../types";

type PortfolioSummaryProps = {
  summary: PortfolioSummaryData;
};

export default function PortfolioSummary({ summary }: PortfolioSummaryProps) {
  return (
    <div className="portfolio-summary-container">
      <div className="summary-card">
        <h3>Total Portfolio Value</h3>

        <p>${summary.totalValue.toFixed(2)}</p>
      </div>

      <div className="summary-card">
        <h3>Total Gain / Loss</h3>

        <p>
          {summary.totalGainLoss < 0 && "-"}${Math.abs(summary.totalGainLoss).toFixed(2)}
        </p>
      </div>

      <div className="summary-card">
        <h3>Overall Return (%)</h3>
        
        <p>
          {summary.totalReturnPercent.toFixed(2)}%
        </p>
      </div>
    </div>
  );
};
