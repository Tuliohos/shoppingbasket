package com.interview.shoppingbasket;

import lombok.Builder;

import java.util.List;

public class FiftyPercentOffPromotion extends Promotion{

    @Builder
    public FiftyPercentOffPromotion(List<String> productCodes){
        super(productCodes);
    }

    @Override
    public double calculateDiscount(BasketItem basketItem) {
        return basketItem.getProductRetailPrice() / 2 * basketItem.getQuantity();
    }
}
