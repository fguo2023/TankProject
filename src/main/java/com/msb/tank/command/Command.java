package com.msb.tank.command;

public abstract class Command {
    public abstract void doit(); // exec, run
    public abstract void undo();
}
