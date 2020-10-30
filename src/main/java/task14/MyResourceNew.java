package task14;

import java.io.FileNotFoundException;

public class MyResourceNew implements AutoCloseable{

    int i;

    public MyResourceNew(int i) {
        this.i = i;
    }

    @Override
    public void close() throws Exception {
        throw new FileNotFoundException("Ресурс " + i + " при закрытии дал сбой!");
    }
}