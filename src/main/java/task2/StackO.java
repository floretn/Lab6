package task2;

import java.util.Arrays;

public class StackO {

    Object[] array = new Object[10];
    int size = 0;

    public void push(Object newElement){
        if(size == array.length){
            array = Arrays.copyOf(array, size + 10);
        }
        array[size] = newElement;
        size++;
    }

    public Object pop(){
        Object popObject = array[size - 1];
        array[size - 1] = null;
        size--;
        return popObject;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public static void main(String[] args) {
        StackO stack = new StackO();
        for (int i = 0; i < 10; i++){
            stack.push("Итерация " + (i + 1));
        }
        stack.push("Расширяем массив");
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }

        stack.push("Kek Cheburek");
        String string = (String) stack.pop();
    }
}
