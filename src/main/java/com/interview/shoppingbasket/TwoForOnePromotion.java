package com.interview.shoppingbasket;

import lombok.Builder;

import java.util.List;

public class TwoForOnePromotion extends Promotion{

    @Builder
    public TwoForOnePromotion(List<String> productCodes){
        super(productCodes);
    }

    @Override
    public double calculateDiscount(BasketItem basketItem) {
        return basketItem.getQuantity() / 2.0 * basketItem.getProductRetailPrice();
    }

}