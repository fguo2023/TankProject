package com.msb.tank.bridge.v3;

import com.msb.tank.bridge.v2.Gift;
import com.msb.tank.bridge.v2.MM;
import com.msb.tank.bridge.v2.WarmGift;

public class GG {
    public void chase(MM mm) {
        Gift g = new WarmGift(new Flower("orchid")); // 温暖的话。
        Gift g1 = new WildGift(new Book("game of throne")); // 野蛮的书
        give(mm, g);
        give(mm, g1);
    }

    private void give(MM mm, Gift g) {
        System.out.println(g + "gived");
    }
}
