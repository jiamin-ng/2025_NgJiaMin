package com.example.coinapi.Controller;

import com.example.coinapi.Model.CoinRequest;
import com.example.coinapi.Model.CoinResponse;
import com.example.coinapi.Service.CoinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coins")
@Validated
public class CoinController {

    @Autowired
    private CoinService coinService;

    @PostMapping("/calculate")
    public ResponseEntity<CoinResponse> calculateCoins(@Valid @RequestBody CoinRequest request) {
        return ResponseEntity.ok(coinService.calculateMinimumCoins(request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
