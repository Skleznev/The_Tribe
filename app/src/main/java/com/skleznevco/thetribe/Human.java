package com.skleznevco.thetribe;

public class Human{
    private int busy;
    private int free;
    private int total;

    public Human(int total){
        this.total = total;
        this.free = total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean incrementBusy() {
        if(busy < total){
            busy++;
            free--;
            return true;
        }
        return false;
    }

    public boolean incrementFree() {
        if (free < total){
            free++;
            busy--;
            return true;
        }
        return false;
    }

    public String getTotal() {
        return String.valueOf(total);
    }

    public String getBusy() {
        return String.valueOf(busy);
    }

    public String getFree() {
        return String.valueOf(free);
    }

    public void addFree(int count){
        total+=count;
        free+=count;
    }

    public void minusFree(int count) {
        total-=count;
        free-=count;
    }
}
