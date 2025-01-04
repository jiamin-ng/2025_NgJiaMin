package com.example.coinapi.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CoinRequest {

    @NotNull
    @Min(0)
    @Max(10000)
    private double targetAmount;

    @NotNull
    private List<Double> denominations;

    // Getters and Setters
    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public List<Double> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Double> denominations) {
        this.denominations = denominations;
    }
}
