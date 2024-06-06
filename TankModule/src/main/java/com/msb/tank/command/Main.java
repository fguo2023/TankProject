package com.msb.tank.command;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Content c = new Content();
        Command insertCommand = new InsertCommand(c);
        insertCommand.doit();
        insertCommand.undo();

        Command copyCommand = new CopyCommand(c);
        copyCommand.doit();
        copyCommand.undo();

        Command deleteCommand = new DeleteCommand(c);
        deleteCommand.doit();
        deleteCommand.undo();

        System.out.println(c.msg);

        //------------------------------------------------------ easy way to implement with cor
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(insertCommand);
        commands.add(copyCommand);
        commands.add(deleteCommand);
        for (Command comm :
                commands) {
            comm.doit();
        }

        System.out.println(c.msg);
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
        System.out.println(c.msg);
        //------------------------------------------------------
       //the complex one can use the linkedlist to implement

    }
}
