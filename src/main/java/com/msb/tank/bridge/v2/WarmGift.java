package com.msb.tank.bridge.v2;

import com.msb.tank.bridge.v3.Flower;
import com.msb.tank.bridge.v3.GiftImpl;

public class WarmGift extends Gift{
    GiftImpl gift;
    public WarmGift(GiftImpl gift) {
       this.gift = gift;
    }
}
