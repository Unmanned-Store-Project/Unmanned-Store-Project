package com.example.unmannedstore;

public class ShoppingItemBean {
    private String itemName;
    private int price;
    private int count;

    public ShoppingItemBean(){
    }

    public ShoppingItemBean(String name, int price){
        this.itemName = name;
        this.price = price;
        this.count = 1;
    }

    public String getName(){
        return itemName;
    }

    public int getPrice() {
        return price;
    }

    public int getCount(){ return count; }

    public void countPlus(){ this.count += 1; }
}
