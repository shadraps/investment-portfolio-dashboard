import { useState, type SubmitEvent} from "react";
import axios from "axios";

type AddHoldingFormProps = {
  apiBase: string;
  handleHoldingAdded: () => void;
};

export default function AddHoldingForm({ apiBase, handleHoldingAdded }: AddHoldingFormProps) {
  const [symbol, setSymbol] = useState("");
  const [shares, setShares] = useState("");
  const [avgCostBasis, setAvgCostBasis] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");

    if (!symbol || !shares || !avgCostBasis) {
      setError("All fields are required.");
      return;
    }

    if (Number(shares) <= 0 || Number(avgCostBasis) <= 0) {
      setError("Shares and cost basis must be positive numbers.");
      return;
    }

    try {
      await axios.post(`${apiBase}/api/holdings`, {
        symbol: symbol.trim().toUpperCase(),
        shares: Number(shares),
        avgCostBasis: Number(avgCostBasis),
        dateAdded: new Date().toISOString().split("T")[0]
      });

      setSymbol("");
      setShares("");
      setAvgCostBasis("");

      handleHoldingAdded();
    } catch (error) {
      setError("Failed to add holding: " + (error as Error).message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="add-holding-form">
      <h3>Add Holding</h3>

      {error && <p className="error-message">{error}</p>}

      <div>
        <label>Symbol</label>
        <input
          type="text"
          value={symbol}
          onChange={(e) => setSymbol(e.target.value)}
          placeholder="AAPL"
        />
      </div>

      <div>
        <label>Shares</label>
        <input
          type="number"
          value={shares}
          onChange={(e) => setShares(e.target.value)}
          step="0.01"
        />
      </div>

      <div>
        <label>Avg Cost Basis</label>
        <input
          type="number"
          value={avgCostBasis}
          onChange={(e) => setAvgCostBasis(e.target.value)}
          step="0.01"
        />
      </div>

      <button type="submit">Add Holding</button>
    </form>
  );
}
