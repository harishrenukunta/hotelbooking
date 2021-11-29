package com.equalexperts.hotelbooking.enums;

public enum Constants {

    NO_RECORDS_ROW_NO(-1);

    Constants(int val){
        this.val = val;
    }

    private int val;

    public int getVal(){
        return this.val;
    }
}
