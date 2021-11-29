package com.equalexperts.hotelbooking.stepdefs;

import com.google.common.util.concurrent.Uninterruptibles;

import java.util.concurrent.TimeUnit;

public class BasePageStepDef {
    public void pause(long inSecs){
        Uninterruptibles.sleepUninterruptibly(inSecs, TimeUnit.SECONDS);
    }
}
