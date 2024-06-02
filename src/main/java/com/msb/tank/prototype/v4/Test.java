package com.msb.tank.prototype.v4;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person) p1.clone();
        System.out.println(p2.age + "" + p2.score);
        System.out.println(p2.loc);

        System.out.println(p1.loc == p2.loc);
        p1.loc.street.reverse();
        System.out.println(p2.loc.street);
    }

}

class Location implements Cloneable {
    StringBuilder street;
    int roomNo;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Location(StringBuilder street, int roomNo) {
        this.street = street;
        this.roomNo = roomNo;
    }

    @Override
    public java.lang.String toString() {
        return "Location{" +
                "street=" + street +
                ", roomNo=" + roomNo +
                '}';
    }
}

// Cloneable 是一般标记性接口，里面没有需要实现的方法
class Person implements Cloneable {
    int age = 8;
    int score = 100;
    Location loc = new Location(new StringBuilder("bj"), 32);

    // 如果需要在其他类想要使用这个方法，必须在这个类实现clone（）并且将protected改成public
    @Override
    public Object clone() throws CloneNotSupportedException {
        Person p = (Person) super.clone();
        p.loc = (Location) loc.clone();
        return p;
    }
}


