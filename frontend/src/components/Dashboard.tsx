import { useEffect, useState } from "react";
import axios from "axios";

import HoldingsTable from "./HoldingsTable";
import AddHoldingForm from "./AddHoldingForm";
import PortfolioSummary from "./PortfolioSummary";

import type { HoldingDTO, PortfolioSummaryData } from "../types";

const API_BASE = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

export default function Dashboard() {
  const [holdings, setHoldings] = useState<HoldingDTO[]>([]);
  const [summary, setSummary] = useState<PortfolioSummaryData>({
    totalValue: 0,
    totalGainLoss: 0,
    totalReturnPercent: 0
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);

      const holdingsRes = await axios.get(`${API_BASE}/api/holdings`);
      const summaryRes = await axios.get(`${API_BASE}/api/portfolio/summary`);
      
      setHoldings(holdingsRes.data);
      setSummary(summaryRes.data);
      setError("");
    } catch (error) {
      setError("Failed to load portfolio data: " + (error as Error).message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleDeleteHolding = async (id: number) => {
    await axios.delete(`${API_BASE}/api/holdings/${id}`);
    await loadData();
  };

  if (loading) {
    return <div className="loading">Loading portfolio...</div>;
  }

  return (
    <div className="dashboard-container">
      <h1>Portfolio Dashboard</h1>
      {error && <div className="error-message">{error}</div>}

      <PortfolioSummary
        summary={summary}
      />

      <AddHoldingForm
        apiBase={API_BASE}
        handleHoldingAdded={loadData}
      />

      <HoldingsTable
        holdings={holdings}
        handleDeleteHolding={handleDeleteHolding}
      />
    </div>
  );
}
