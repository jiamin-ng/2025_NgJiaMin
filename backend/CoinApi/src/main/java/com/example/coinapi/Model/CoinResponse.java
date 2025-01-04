package com.example.coinapi.Model;

import java.util.List;

public class CoinResponse {
    private List<Double> coins;

    public CoinResponse(List<Double> coins) {
        this.coins = coins;
    }

    // Getters and Setters
    public List<Double> getCoins() {
        return coins;
    }

    public void setCoins(List<Double> coins) {
        this.coins = coins;
    }
}
