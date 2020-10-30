package task2;

import java.util.Arrays;

public class StackE<E> {

    private E[] array;
    private int size;

    @SuppressWarnings({"unchecked", "unused"})
    public StackE(Class<E> cl) {
        array = (E[]) java.lang.reflect.Array.newInstance(cl,10);
        size = 0;
    }

    public void push(E newElement){
        if(size == array.length){
            array = Arrays.copyOf(array, size + 10);
        }
        array[size] = newElement;
        size++;
    }

    public E pop(){
        E popElement = array[size - 1];
        array[size - 1] = null;
        size--;
        return popElement;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public static void main(String[] args) {
        StackE<String> stack = new StackE<>(String.class);
        for (int i = 0; i < 10; i++){
            stack.push("Итерация " + (i + 1));
        }
        stack.push("Расширяем массив");
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }

        stack.push("Kek Cheburek");
        String string = stack.pop();
    }
}
