package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        Map<String, BasketItem> itemsMap = new HashMap<>();

        items.forEach(item ->{
            if(!itemsMap.containsKey(item.getProductCode())) {
                int basketItemQuantitySum = items.stream()
                    .filter(i -> i.getProductCode().equals(item.getProductCode()))
                    .mapToInt(BasketItem::getQuantity)
                    .sum();

                itemsMap.put(item.getProductCode(),
                    new BasketItem(item.getProductCode(), item.getProductName(),
                        basketItemQuantitySum));
            }
        });

        items = List.copyOf(itemsMap.values());
    }
}
