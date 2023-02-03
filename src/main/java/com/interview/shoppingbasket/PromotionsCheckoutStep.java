package com.interview.shoppingbasket;

import java.util.List;

public class PromotionsCheckoutStep implements CheckoutStep{
    private PromotionsService promotionsService;

    public PromotionsCheckoutStep(PromotionsService promotionsService){
        this.promotionsService = promotionsService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        List<Promotion> promotions = promotionsService.getPromotions(basket);
        checkoutContext.setPromotions(promotions);
    }
}