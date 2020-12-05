package bigDop;

import dop.Manager;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassWithTypeParameter<E, G, H> {
    G g;
    E[] es;

    public G getG() {
        return g;
    }

    public void setG(G g) {
        this.g = g;
    }

    ArrayList<G> al;

    HashMap<Number, H> hashMap;

    ArrayList<? extends Comparable<H>> ah;

    Comparable<? super ArrayList<Manager>> alMan;

    ArrayList<ArrayList<Manager>> alManagers;
}
