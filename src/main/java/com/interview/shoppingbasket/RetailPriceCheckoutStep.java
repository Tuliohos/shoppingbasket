package com.interview.shoppingbasket;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        Basket basket = checkoutContext.getBasket();
        retailTotal = 0.0;

        for (BasketItem basketItem : basket.getItems()) {
            int quantity = basketItem.getQuantity();
            double price = pricingService.getPrice(basketItem.getProductCode());
            basketItem.setProductRetailPrice(price);

            Promotion promotion = checkoutContext.getPromotions()
                .stream()
                .filter(
                    p -> p.getProductCodes().contains(basketItem.getProductCode()))
                .findFirst()
                .orElse(null);

            double discount = applyPromotion(promotion, basketItem);

            retailTotal += quantity * price - discount;
        }

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem item) {
        return promotion != null ? promotion.calculateDiscount(item) : 0.0;
    }
}
