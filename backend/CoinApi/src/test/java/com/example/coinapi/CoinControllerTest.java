package com.example.coinapi;

import com.example.coinapi.Controller.CoinController;
import com.example.coinapi.Model.CoinRequest;
import com.example.coinapi.Model.CoinResponse;
import com.example.coinapi.Service.CoinService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoinController.class)
public class CoinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CoinService coinService;

    @Test
    void calculatedCoins_validInput_returns200() throws Exception {
        CoinRequest request = new CoinRequest();
        request.setTargetAmount(1.25);
        request.setDenominations(List.of(0.01, 0.05, 0.1, 0.5, 1.0));

        Mockito.when(coinService.calculateMinimumCoins(Mockito.any(CoinRequest.class)))
                .thenReturn(new CoinResponse(List.of(0.05, 0.1, 0.1, 1.0)));

        mockMvc.perform(post("/api/coins/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "targetAmount": 1.25,
                           "denominations": [0.01, 0.05, 0.1, 0.5, 1.0]
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coins").isArray())
                .andExpect(jsonPath("$.coins[0]").value(0.05))
                .andExpect(jsonPath("$.coins[1]").value(0.1))
                .andExpect(jsonPath("$.coins[2]").value(0.1))
                .andExpect(jsonPath("$.coins[3]").value(1.0));
    }

    @Test
    void calculatedCoins_negativeTargetAmount_returns400() throws Exception {
        mockMvc.perform(post("/api/coins/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "targetAmount": -2.35,
                          "denominations": [0.05, 0.1, 0.2, 0.5]
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculatedCoins_invalidDenominations_returns400() throws Exception {
        Mockito.when(coinService.calculateMinimumCoins(Mockito.any(CoinRequest.class)))
                .thenThrow(new IllegalArgumentException("Invalid coin denomination."));

        mockMvc.perform(post("/api/coins/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "targetAmount": 2.0,
                          "denominations": [0.03, 0.05]
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid coin denomination."));
    }

    @Test
    void calculatedCoins_emptyDenominations_returns400() throws Exception {
        Mockito.when(coinService.calculateMinimumCoins(Mockito.any(CoinRequest.class)))
                .thenThrow(new IllegalArgumentException("Cannot make target amount with the given denominations."));

        mockMvc.perform(post("/api/coins/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "targetAmount": 1.0,
                          "denominations": []
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot make target amount with the given denominations."));
    }

    @Test
    void calculatedMinimumCoins_missingDenomination_returns400() throws Exception {
        Mockito.when(coinService.calculateMinimumCoins(Mockito.any(CoinRequest.class)))
                .thenThrow(new IllegalArgumentException("Cannot make target amount with the given denominations."));

        mockMvc.perform(post("/api/coins/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "targetAmount": 2.35,
                          "denominations": [1.0, 0.5]
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cannot make target amount with the given denominations."));
    }

    @Test
    void calculatedCoins_largeTargetAmount_returns400() throws Exception {
        mockMvc.perform(post("/api/coins/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "targetAmount": 10001,
                          "denominations": [1000.0, 100.0, 0.5]
                        }
                        """))
                .andExpect(status().isBadRequest());
    }
}
