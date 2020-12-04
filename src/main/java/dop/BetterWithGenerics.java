package dop;

public class BetterWithGenerics {

    public static int sum(ArrayList integers){
        int sum = 0;
        for (int i = 0; i < integers.size; i++){
            sum += (Integer) integers.get(i);
        }
        return sum;
    }

    public static int sumGood(java.util.ArrayList<Integer> integers){
        int sum = 0;
        for (Integer i : integers){
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        ArrayList arrayList1 = new ArrayList();
        arrayList1.add(12);
        arrayList1.add(151);
        System.out.println(sum(arrayList1));

        java.util.ArrayList<Integer> arrayList2 = new java.util.ArrayList<>();
        arrayList2.add(12);
        arrayList2.add(151);
        System.out.println(sumGood(arrayList2));

        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("Kek");
        System.out.println(sum(arrayList3));
    }
}
