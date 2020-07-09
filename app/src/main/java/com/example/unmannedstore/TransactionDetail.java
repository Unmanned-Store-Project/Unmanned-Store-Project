package com.example.unmannedstore;

public class TransactionDetail {
    private String id;
    private String time;
    private char type;
    private Double money;
    private String mine;
    private String other;

    public String getMine() {
        return mine;
    }

    public void setMine(String mine) {
        this.mine = mine;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return /*"TransactionDetail{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", type=" + type +
                ", money=" + money +
                ", mine='" + mine + '\'' +
                ", other='" + other + '\'' +
                '}'*/
                //test:
                "type/money/mine/other/time/id/" +
                        "+/1000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/2000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "+/3000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/4000/3217/8888/2020-07-07 21:00/1234567890/"+"" +
                        "+/5000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/6000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "+/7000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/8000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "+/9000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/1000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "+/2000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/3000/3217/8888/2020-07-07 21:00/1234567890/"+"" +
                        "+/4000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/5000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "+/6000/3217/8888/2020-07-07 21:00/1234567890/"+
                        "-/7000/3217/8888/2020-07-07 21:00/1234567890/"
                ;
    }
}
