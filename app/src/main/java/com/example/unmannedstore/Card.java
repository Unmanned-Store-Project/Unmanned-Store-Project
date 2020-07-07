package com.example.unmannedstore;

import cn.bmob.v3.BmobObject;

public class Card extends BmobObject {
    private String cardId;
    private String passWord;
    private Double balance;
    private String userName;

    public Card() {
        this.balance = 1000.00;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", passWord='" + passWord + '\'' +
                ", balance=" + balance +
                ", userName='" + userName + '\'' +
                '}';
    }
}
