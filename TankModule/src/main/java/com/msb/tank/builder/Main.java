package com.msb.tank.builder;

public class Main {
    public static void main(String[] args) {
        TerrainBuilder builder = new ComputerTerrainBuilder();
        Terrain t = builder.buildFort().buildMine().buildWall().build();
        Person p = new Person.PersonBuilder().basicInfo(1, "leo",20).weight(200).score(100).build(); // can only new the PersonBuilder under the Person class
    }
}
