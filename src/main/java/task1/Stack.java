package task1;

import task2.StackE;

import java.util.ArrayList;

public class Stack<E> {

    ArrayList<E> stack = new ArrayList<>();

    public void push(E newElement){
        stack.add(newElement);
    }

    public E pop(){
        E el = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return el;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
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
