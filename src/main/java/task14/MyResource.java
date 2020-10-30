package task14;

public class MyResource implements AutoCloseable{

    int i;

    public MyResource(int i) {
        this.i = i;
    }

    @Override
    public void close() throws Exception {
        throw new ExceptionForMyResource("Ресурс " + i + " при закрытии дал сбой!");
    }
}
