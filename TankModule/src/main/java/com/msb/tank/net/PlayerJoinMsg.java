package com.msb.tank.net;
import com.msb.tank.DIR;
import com.msb.tank.Group;

import java.util.UUID;

public class PlayerJoinMsg extends Msg{
    private static final MsgType TYPE = MsgType.PlayerJoin;
    int x, y;
    DIR dir;
    boolean moving;
    Group group;
    public UUID id;
    String name;

    public PlayerJoinMsg() {
    }

    public static void main(String[] args) {

    }



}
