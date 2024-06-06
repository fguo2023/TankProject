package com.msb.tank.prototype.v1;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person p1 = new Person();
        Person p2 = (Person) p1.clone();
        System.out.println(p2.age + "" + p2.score);
        System.out.println(p2.loc);

        System.out.println(p1.loc == p2.loc);
        System.out.println(p1== p2);
        p1.loc.street = "sh";
        System.out.println(p2.loc);
    /*  result: 潜克隆
        8100
        Location{street='bj', roomNo=32}
        true
        false
        Location{street='sh', roomNo=32}*/
    }
}

class Location {
    String street;
    int roomNo;

    public Location(String street, int roomNo) {
        this.street = street;
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", roomNo=" + roomNo +
                '}';
    }
}

// Cloneable 是一般标记性接口，里面没有需要实现的方法
class Person implements Cloneable {
    int age = 8;
    int score = 100;
    Location loc = new Location("bj", 32);

    // 如果需要在其他类想要使用这个方法，必须在这个类实现clone（）并且将protected改成public
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


