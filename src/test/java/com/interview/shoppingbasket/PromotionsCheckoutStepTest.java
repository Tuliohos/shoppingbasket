package com.interview.shoppingbasket;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

public class PromotionsCheckoutStepTest {

    @Test
    void promotionsCheckoutStepTest(){
        CheckoutContext checkoutContext = Mockito.mock(CheckoutContext.class);
        PromotionsService promotionsService = Mockito.mock(PromotionsService.class);
        PromotionsCheckoutStep promotionCheckoutStep = new PromotionsCheckoutStep(promotionsService);

        promotionCheckoutStep.execute(checkoutContext);

        Mockito.verify(checkoutContext).getBasket();
        Mockito.verify(promotionsService).getPromotions(any());
        Mockito.verify(checkoutContext).setPromotions(anyList());
    }
}
