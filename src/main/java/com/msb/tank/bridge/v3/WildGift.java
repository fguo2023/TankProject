package com.msb.tank.bridge.v3;

import com.msb.tank.bridge.v2.Gift;

public class WildGift extends Gift {
    private GiftImpl giftImpl;
    public WildGift(GiftImpl giftImpl) {
        this.giftImpl = giftImpl;
    }
}
