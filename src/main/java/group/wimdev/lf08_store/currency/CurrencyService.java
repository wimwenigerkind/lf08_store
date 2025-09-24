package group.wimdev.lf08_store.currency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import group.wimdev.lf08_store.exceptionhandling.UnsupportedCurrencyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";
    private static final int DECIMAL_PLACES = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    public CurrencyService(@Value("${currency.api.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
    }

    public Double convert(Double price, String currency, String targetCurrency) {
        if (!isValidConversionRequest(price, currency, targetCurrency)) {
            return price;
        }

        if (targetCurrency.equals(currency)) {
            return price;
        }

        try {
            double exchangeRate = fetchExchangeRate(currency, targetCurrency);
            return calculateConvertedPrice(price, exchangeRate);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("unsupported-code")) {
                throw new UnsupportedCurrencyException(targetCurrency);
            }
            throw new RuntimeException("Currency conversion failed", e);
        }
    }

    private boolean isValidConversionRequest(Double price, String currency, String targetCurrency) {
        return price != null && currency != null && targetCurrency != null;
    }

    private double fetchExchangeRate(String currency, String targetCurrency) throws Exception {
        String url = buildApiUrl(currency, targetCurrency);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);

        if (!jsonNode.has("conversion_rate")) {
            throw new RuntimeException("No conversion rate found in API response");
        }

        return jsonNode.get("conversion_rate").asDouble();
    }

    private String buildApiUrl(String currency, String targetCurrency) {
        return BASE_URL + "/" + apiKey + "/pair/" + currency + "/" + targetCurrency;
    }

    private Double calculateConvertedPrice(Double originalPrice, double exchangeRate) {
        BigDecimal convertedPrice = BigDecimal.valueOf(originalPrice)
                .multiply(BigDecimal.valueOf(exchangeRate));
        return convertedPrice.setScale(DECIMAL_PLACES, ROUNDING_MODE).doubleValue();
    }
}