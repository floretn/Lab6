package task4;

import java.util.ArrayList;

public class Table<K, V> {

    private ArrayList<Entry<K, V>> arrayList = new ArrayList<Entry<K, V>>();

    private static class Entry<K, V>{

        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public void set(K key, V value){
        for(Entry<K, V> element : arrayList){
            if(element.key.equals(key)){
                element.value = value;
                return;
            }
        }
        arrayList.add(new Entry<K, V>(key, value));
    }

    public V get(K key){
        for(Entry<K, V> element : arrayList){
            if(element.key.equals(key)){
                return element.value;
            }
        }
        return null;
    }

    public void delete(K key){
        arrayList.removeIf(element -> element.key.equals(key));
    }

    @Override
    public String toString() {
        return "Table{" +
                "arrayList=" + arrayList +
                '}';
    }

    public static void main(String[] args) {
        Table<Integer, String> table = new Table<>();
        table.set(1, "One");
        table.set(2, "Two");
        System.out.println(table.get(1));
        System.out.println(table.get(2));
        System.out.println(table);
        table.set(1, "Three");
        System.out.println(table.get(1));
        System.out.println(table);
        table.delete(2);
        System.out.println(table.get(2));
        System.out.println(table);
    }
}
