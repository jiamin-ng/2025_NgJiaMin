import './App.css';
import React, { useState } from 'react';
import axios from 'axios';


const App = () => {
  const [targetAmount, setTargetAmount] = useState("");
  const [denominations, setDenominations] = useState("");
  const [result, setResult] = useState([]);
  const [error, setError] = useState("");
  const [formErrors, setFormErrors] = useState({});

  const validateInputs = () => {
    const errors = {};

    // Validate target amount
    if (isNaN(targetAmount) || targetAmount < 0 || targetAmount > 10000) {
      errors.targetAmount = "Target amount must be between 0 and 10,000.00.";
    }

    // Acceptable denominations
    const acceptableDenoms = [
      0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0
    ];

    // Validate denominations
    const denomArray = denominations
      .split(",")
      .map((denom) => denom.trim())
      .filter((denom) => denom !== "") // Remove empty values
      .map((denom) => parseFloat(denom));
    const invalidDenoms = denomArray.filter(
      (denom) => isNaN(denom) || denom < 0 || !acceptableDenoms.includes(denom)
    );
    if (denomArray.some((denom) => isNaN(denom) || denom <= 0)) {
      errors.denominations =
        "Denominations must be positive numbers, separated by commas.";
    } else if (invalidDenoms.length > 0) {
      errors.denominations = 
        `Invalid denominations entered: ${invalidDenoms.join(", ")}. Allowed values are: ${acceptableDenoms.join(", ")}.`;
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0; // Return true if there are no errors
  };

  const handleCalculate = async (e) => {
    e.preventDefault();
    setError(""); // Clear previous errors
    setResult([]); // Clear previous results

    // Validate inputs
    if (!validateInputs()) return;

    const requestData = {
      targetAmount: parseFloat(targetAmount),
      denominations: denominations.split(",").map((d) => parseFloat(d.trim())),
    };

    try {
      const response = await axios.post(
        "/api/coins/calculate", 
        requestData
      );
      setResult(response.data.coins);
    } catch (err) {
      const errorMessage = err.response?.data?.message || "An error occurred.";
      setError(errorMessage);
    }
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Coin Calculator</h1>
      <div className="card shadow">
        <div className="card-body">
          <form onSubmit={handleCalculate}>
            <div className="mb-3">
              <label className="form-label">Target Amount:</label>
              <input
                type="number"
                step="0.01"
                className={`form-control ${
                  formErrors.targetAmount ? "is-invalid" : ""
                }`}
                value={targetAmount}
                onChange={(e) => setTargetAmount(e.target.value)}
                required
              />
              {formErrors.targetAmount && (
                <div className="invalid-feedback">{formErrors.targetAmount}</div>
              )}
            </div>
            <div className="mb-3">
              <label className="form-label">Denominations (comma-separated):</label>
              <input
                type="text"
                className={`form-control ${
                  formErrors.denominations ? "is-invalid" : ""
                }`}
                value={denominations}
                onChange={(e) => setDenominations(e.target.value)}
                placeholder="e.g., 0.01, 0.05, 0.1, 0.5"
                required
              />
              {formErrors.denominations && (
                <div className="invalid-feedback">{formErrors.denominations}</div>
              )}
            </div>
            <button type="submit" className="btn btn-primary w-100">Calculate</button>
          </form>

          {error && <p className="text-danger mt-3">{error}</p>}

          {result.length > 0 && (
            <div className="mt-4">
              <h5>Calculated Coins:</h5>
              <ul className="list-group">
                {result.map((coin, index) => (
                  <li key={index} className="list-group-item">
                    {coin.toFixed(2)}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default App;
