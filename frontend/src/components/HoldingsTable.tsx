import type { HoldingDTO } from "../types";

type HoldingsTableProps = {
  holdings: HoldingDTO[];
  handleDeleteHolding: (id: number) => void;
};

export default function HoldingsTable({ holdings, handleDeleteHolding }: HoldingsTableProps) {
  return (
    <div className="holdings-table">
      <table>
        <thead>
          <tr>
            <th>Ticker</th>
            <th>Shares</th>
            <th>Avg Cost</th>
            <th>Current Price</th>
            <th>Current Value</th>
            <th>P&L ($)</th>
            <th>P&L (%)</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {holdings.map((h) => {
            return (
              <tr key={h.id}>
                <td>{h.symbol}</td>
                <td>{h.shares}</td>
                <td>${h.avgCostBasis.toFixed(2)}</td>
                <td>${h.currentPrice.toFixed(2)}</td>
                <td>${h.currentValue.toFixed(2)}</td>

                <td>
                  ${h.gainLoss.toFixed(2)}
                </td>

                <td>
                  {h.percentReturn.toFixed(2)}%
                </td>

                <td>
                  <button onClick={() => handleDeleteHolding(h.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};
