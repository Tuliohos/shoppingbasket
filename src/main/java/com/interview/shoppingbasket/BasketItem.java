package com.interview.shoppingbasket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/*@NoArgsConstructor annotation was added thinking in a big project were the addition of a constructor possibly affect many cases
of usages. Using this, te methods that instantiates the BasketItem class without parameters
would continue working. As we can see in the Basket.add method*/
public class BasketItem {
    private String productCode;
    private String productName;
    private int quantity;
    private double productRetailPrice;

    public BasketItem (String productCode, String productName, int quantity){
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
    }
}