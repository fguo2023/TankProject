package com.msb.tank.visitor;

public class Computer {
    ComputerPart cpu = new CPU();
    ComputerPart memory = new Memory();
    ComputerPart board = new Board();

    public void accept(Visitor v) {
        this.cpu.accept(v);
        this.memory.accept(v);
        this.board.accept(v);
    }

    public static void main(String[] args) {
        PersonelVisitor personelVisitor = new PersonelVisitor();
        new Computer().accept(personelVisitor);
        System.out.println(personelVisitor.totalPrice);

        CorpVisitor visitor = new CorpVisitor();
        new Computer().accept(visitor);
        System.out.println(visitor.totalPrice);
        // build a computer, cpu, memory, board, if there is a person come
        // if(is instance of person) then return the price 30, if is instance of corp, then return 10 for every class
    }
}

abstract class ComputerPart {
    abstract void accept(Visitor v);

    // this is the purpose method we need to use.
    abstract double getPrice();
}

class Board extends ComputerPart {

    @Override
    void accept(Visitor v) {
        v.visitBoard(this);
    }

    @Override
    double getPrice() {
        return 200.0;
    }
}

class CPU extends ComputerPart {

    @Override
    void accept(Visitor v) {
        v.visitCpu(this);
    }

    @Override
    double getPrice() {
        return 1000.0;
    }
}

class Memory extends ComputerPart {

    @Override
    void accept(Visitor v) {
        v.visitMemory(this);
    }

    @Override
    double getPrice() {
        return 300.0;
    }
}

// this visitor class must be the fixed structure, if we need to add a disk or anything else, we need to modify this class.
interface Visitor {
    void visitCpu(CPU cpu);

    void visitMemory(Memory memory);

    void visitBoard(Board board);
}

// what strategy is using for the Visitor, could have more than one Visitor, but define its own strategy
// after creating the visitor class, then this is dynamic, we could have more than one this class to implement visitor
// the purpose is to build a Computer, to build a computer you need to know each part of the price with different discount
//
class PersonelVisitor implements Visitor {
    double totalPrice = 0.0;

    @Override
    public void visitCpu(CPU cpu) {
        totalPrice += cpu.getPrice() * 0.9;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice += memory.getPrice() * 0.85;
    }

    @Override
    public void visitBoard(Board board) {
        totalPrice += board.getPrice() * 0.95;
    }
}

class CorpVisitor implements Visitor {
    double totalPrice = 0.0;

    @Override
    public void visitCpu(CPU cpu) {
        totalPrice += cpu.getPrice() * 0.6;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice += memory.getPrice() * 0.75;
    }

    @Override
    public void visitBoard(Board board) {
        totalPrice += board.getPrice() * 0.75;
    }
}