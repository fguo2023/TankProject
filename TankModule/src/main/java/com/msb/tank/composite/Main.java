package com.msb.tank.composite;

import java.awt.*;
import java.util.ArrayList;

abstract class Node {
    abstract public void p();
}

class LeafNode extends Node {
    String content;

    public LeafNode(String content) {
        this.content = content;
    }

    @Override
    public void p() {
        System.out.println(content);
    }
}

class BranchNode extends Node {
    ArrayList<Node> nodes = new ArrayList<>();
    String name;

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public BranchNode(String name) {
        this.name = name;
    }

    public void add(Node n){
        nodes.add(n);
    }

    @Override
    public void p() {
        System.out.println(name);
    }
}

public class Main {
    public static void main(String[] args) {
       BranchNode root = new BranchNode("root");
       BranchNode chapter1 = new BranchNode("chapter1");
       BranchNode chapter2 = new BranchNode("chapter2");
       Node c11 = new LeafNode("c11");
       Node c12 = new LeafNode("c12");

       root.add(chapter1);
       root.add(chapter2);
       chapter1.add(c11);
       chapter1.add(c12);

       tree(root, 0);

    }
    static void tree(Node b, int depth){
        for (int i = 0; i < depth; i++) {
            System.out.print("--");
        }
       b.p();
       if(b instanceof BranchNode){
           for(Node n : ((BranchNode)b).getNodes()){
               tree(n, depth + 1);
           }
       }
    }
}
