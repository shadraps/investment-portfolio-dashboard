export interface HoldingDTO {
  id: number;
  symbol: string;
  shares: number;
  avgCostBasis: number;
  currentPrice: number;
  currentValue: number;
  gainLoss: number;
  percentReturn: number;
};

export interface PortfolioSummaryData {
  totalValue: number;
  totalGainLoss: number;
  totalReturnPercent: number;
};
