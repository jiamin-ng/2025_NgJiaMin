package com.example.coinapi;

import com.example.coinapi.Model.CoinRequest;
import com.example.coinapi.Model.CoinResponse;
import com.example.coinapi.Service.CoinService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoinServiceTest {

    private final CoinService coinService = new CoinService();

    @Test
    void calculatedMinimumCoins_validInput_returnsExpectedCoins() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(1.25);
        request.setDenominations(List.of(0.01, 0.05, 0.1, 0.5, 1.0));

        CoinResponse response = coinService.calculateMinimumCoins(request);

        assertNotNull(response);
        assertEquals(List.of(0.05, 0.1, 0.1, 1.0), response.getCoins());
    }

    @Test
    void calculatedMinimumCoins_zeroTargetAmount_returnsEmptyList() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(0.0);
        request.setDenominations(List.of(0.01, 0.05, 0.1));

        CoinResponse response = coinService.calculateMinimumCoins(request);

        assertNotNull(response);
        assertTrue(response.getCoins().isEmpty());
    }

    @Test
    void calculatedMinimumCoins_invalidDenominations_throwsException() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(2.0);
        request.setDenominations(List.of(0.03, 0.05));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> coinService.calculateMinimumCoins(request)
        );
        assertEquals("Invalid coin denomination: 0.03", exception.getMessage());
    }

    @Test
    void calculatedMinimumCoins_emptyDenominations_throwsException() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(1.0);
        request.setDenominations(List.of());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> coinService.calculateMinimumCoins(request)
        );
        assertEquals("Cannot make target amount with the given denominations.", exception.getMessage());
    }

    @Test
    void calculatedMinimumCoins_missingDenomination_throwsException() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(2.35);
        request.setDenominations(List.of(1.0, 0.5));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> coinService.calculateMinimumCoins(request)
        );
        assertEquals("Cannot make target amount with the given denominations.", exception.getMessage());
    }

    @Test
    void calculatedMinimumCoins_negativeTargetAmount_throwsException() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(-2.35);
        request.setDenominations(List.of(0.05, 0.1, 0.2, 0.5));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> coinService.calculateMinimumCoins(request)
        );
        assertEquals("Target amount must be between 0 and 10,000.00.", exception.getMessage());
    }

    @Test
    void calculatedMinimumCoins_largeTargetAmount_throwsException() {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(10001);
        request.setDenominations(List.of(1000.0, 100.0, 0.5));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> coinService.calculateMinimumCoins(request)
        );
        assertEquals("Target amount must be between 0 and 10,000.00.", exception.getMessage());
    }
}
