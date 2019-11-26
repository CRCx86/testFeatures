package solution;

import java.util.ArrayList;
import java.util.List;

public class Generics0101 {

    public static void main(String[] args) {
        List<A> l = new ArrayList<>();
        A a = new A(new Integer(1), new String("123"));
        l.add(a);
        l.forEach(item -> {
            System.out.println(item.getId() + " " + item.getName());
        });
    }

}

class A<E, K> {

    E id;

    K name;

    public A(E id, K name) {
        this.id = id;
        this.name = name;
    }

    public E getId() {
        return id;
    }

    public void setId(E id) {
        this.id = id;
    }

    public K getName() {
        return name;
    }

    public void setName(K name) {
        this.name = name;
    }
}
