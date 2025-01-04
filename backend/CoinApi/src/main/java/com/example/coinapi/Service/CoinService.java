package com.example.coinapi.Service;

import com.example.coinapi.Model.CoinRequest;
import com.example.coinapi.Model.CoinResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoinService {

    public CoinResponse calculateMinimumCoins(CoinRequest request) {
        double targetAmount = request.getTargetAmount();
        List<Double> denominations = request.getDenominations();

        // Validate target amount
        if (targetAmount < 0 || targetAmount > 10000) {
            throw new IllegalArgumentException("Target amount must be between 0 and 10,000.00.");
        }

        // Validate denominations
        List<Double> validDenominations = List.of(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);
        for (Double denom : denominations) {
            if (!validDenominations.contains(denom)) {
                throw new IllegalArgumentException("Invalid coin denomination: " + denom);
            }
        }

        // Convert to cents to avoid rounding issues
        int targetInCents = (int) Math.round(targetAmount * 100);
        List<Integer> denomInCents = denominations.stream()
                .map(denom -> (int)Math.round(denom * 100))
                .sorted(Comparator.reverseOrder())
                .toList();

        List<Double> result = new ArrayList<>();
        int remainAmount = targetInCents;

        for (int denom : denomInCents) {
            while (remainAmount >= denom) {
                remainAmount -= denom;
                result.add((double) denom / 100.00);    // Convert denom back to dollars
            }
        }

        // Handle leftover amount
        if (remainAmount > 0) {
            throw new IllegalArgumentException("Cannot make target amount with the given denominations.");
        }

        // Sort results in ascending order
        Collections.sort(result);

        return new CoinResponse(result);
    }

}

