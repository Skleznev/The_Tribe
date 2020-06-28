package com.skleznevco.thetribe.Models;

public class Item {
    private int positive;
    private int negative;
    private int total;
    private int countWorkers;

    public Item(int total) {
        this.total = total;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public void addNegative(int negative) {
        this.negative += negative;
    }

    public void calculateTotal() {
        this.total += positive - negative;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return String.valueOf(negative);
    }

    public String getPositive() {
        return String.valueOf(positive);
    }

    public String getTotal() {
        return String.valueOf(total);
    }

    public int getTotalInt() {
        return total;
    }

    public int getCountWorkers() {
        return countWorkers;
    }

    public void setCountWorkers(int countWorkers) {
        this.countWorkers = countWorkers;
    }

    public void incrementCountWorkers() {
        countWorkers++;
    }

    public void decrementCountWorkers() {
        countWorkers--;
    }

    public void addPositive(int parseInt) {
        this.positive += parseInt;
    }

    public void minusTotal(int parseInt) {
        total -= parseInt;
    }

    public void addTotal(int parseInt) {
        total += parseInt;
    }

    public void setTotal(int i) {
        this.total = i;
    }
}
