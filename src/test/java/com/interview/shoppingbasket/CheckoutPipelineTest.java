package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CheckoutPipelineTest {

    private static final String PRODUCT_CODE_ONE = "productCode1";
    private static final String PRODUCT_CODE_TWO = "productCode2";
    private static final String PRODUCT_CODE_THREE = "productCode3";
    private static final String PRODUCT_CODE_FOUR = "productCode4";

    CheckoutPipeline checkoutPipeline;

    Basket basket;

    @InjectMocks
    BasketConsolidationCheckoutStep basketConsolidationCheckoutStep;

    @InjectMocks
    PromotionsCheckoutStep promotionsCheckoutStep;

    @InjectMocks
    RetailPriceCheckoutStep retailPriceCheckoutStep;

    @Mock
    PromotionsService promotionsService;

    @Mock
    PricingService pricingService;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
        basket = new Basket();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        basket.add(PRODUCT_CODE_ONE,"productName1", 5);
        basket.add(PRODUCT_CODE_ONE, "productName1", 2);
        basket.add(PRODUCT_CODE_TWO, "productName2", 10);
        basket.add(PRODUCT_CODE_THREE, "productName3", 3);
        basket.add(PRODUCT_CODE_FOUR, "productName4", 20);

        when(promotionsService.getPromotions(any(Basket.class))).thenReturn(getPromotionsList());
        when(pricingService.getPrice(PRODUCT_CODE_ONE)).thenReturn(10.0);
        when(pricingService.getPrice(PRODUCT_CODE_TWO)).thenReturn(2.5);
        when(pricingService.getPrice(PRODUCT_CODE_THREE)).thenReturn(6.0);
        when(pricingService.getPrice(PRODUCT_CODE_FOUR)).thenReturn(1.0);

        double retailTotal = 7*10.0 + 10*2.5 + 3*6.0 + 20*1.0;
        double discountTotal = 35.0 + 12.5 + 1.8;

        checkoutPipeline.addStep(basketConsolidationCheckoutStep);
        checkoutPipeline.addStep(promotionsCheckoutStep);
        checkoutPipeline.addStep(retailPriceCheckoutStep);

        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(retailTotal - discountTotal, paymentSummary.getRetailTotal());
    }

    private List<Promotion> getPromotionsList(){
        return List.of(
            TwoForOnePromotion.builder()
                .productCodes(List.of(PRODUCT_CODE_ONE))
                .build(),
            FiftyPercentOffPromotion.builder()
                .productCodes(List.of(PRODUCT_CODE_TWO))
                .build(),
            TenPercentOffPromotion.builder()
                .productCodes(List.of(PRODUCT_CODE_THREE))
                .build()
        );
    }
}