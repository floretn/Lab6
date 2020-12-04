package bigDop;

public class NotGenericClass extends GenericSuperClass<Integer> {

    public NotGenericClass(Integer integer) {
        super(integer);
    }

    public void setT(Integer t){
        this.t = t;
    }
}
