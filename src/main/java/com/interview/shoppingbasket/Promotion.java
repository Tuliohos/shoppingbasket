package com.interview.shoppingbasket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class Promotion {

    private List<String> productCodes;

    public abstract double calculateDiscount(BasketItem basketItem);

}