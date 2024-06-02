package com.msb.tank.bridge.v3;

import com.msb.tank.bridge.v2.Gift;

public class WarmGift extends Gift {
    private GiftImpl giftImpl;
    public WarmGift(GiftImpl giftImpl) {
        this.giftImpl = giftImpl;
    }
}
