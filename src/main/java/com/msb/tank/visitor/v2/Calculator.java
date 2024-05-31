package com.msb.tank.visitor.v2;

public class Calculator {
    Node operatorNode = new OperatorNode();
    Node operandNode = new OperandNode();
    Node statementNode = new StatementNode();

    public void accept(Visitor v) {
        operatorNode.accept(v);
        operandNode.accept(v);
        statementNode.accept(v);
    }

    public static void main(String[] args) {
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
        CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
        new Calculator().accept(codeGeneratorVisitor);
    }
}

abstract class Node {
    abstract void accept(Visitor v);
}

interface Visitor {
    void visitOperator(OperatorNode operatorNode);

    void visitOperand(OperandNode operandNode);

    void visitStatement(StatementNode statementNode);
}

// you could have any type of the visitor
class TypeCheckVisitor implements Visitor {
    @Override
    public void visitOperator(OperatorNode operatorNode) {
        System.out.println("check operator type");
    }

    @Override
    public void visitOperand(OperandNode operandNode) {
        System.out.println("check operand type");
    }

    @Override
    public void visitStatement(StatementNode statementNode) {
        System.out.println("check statement type");
    }
}

class CodeGeneratorVisitor implements Visitor {

    @Override
    public void visitOperator(OperatorNode operatorNode) {
        System.out.println("check operator code generator");
    }

    @Override
    public void visitOperand(OperandNode operandNode) {
        System.out.println("check operand code generator");
    }

    @Override
    public void visitStatement(StatementNode statementNode) {
        System.out.println("check statementNode generator");
    }
}

class PrettyPrintVisitor implements Visitor {

    @Override
    public void visitOperator(OperatorNode operatorNode) {

    }

    @Override
    public void visitOperand(OperandNode operandNode) {

    }

    @Override
    public void visitStatement(StatementNode statementNode) {

    }
}

class StatementNode extends Node {

    @Override
    public void accept(Visitor v) {
        v.visitStatement(this);
    }
}

class OperatorNode extends Node {

    @Override
    public void accept(Visitor v) {
        v.visitOperator(this);
    }
}

class OperandNode extends Node {
    @Override
    public void accept(Visitor v) {
        v.visitOperand(this);
    }
}
