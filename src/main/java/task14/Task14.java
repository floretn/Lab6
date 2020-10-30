package task14;

import java.util.ArrayList;

public class Task14 {

    public static <T extends AutoCloseable> void closeAll(ArrayList<T> elements) throws Exception {
        Exception exception = null;
        for (T el : elements){
            try{
                el.close();
            }catch (Exception ex){
                if (exception == null){
                    exception = ex;
                    continue;
                }
                exception.addSuppressed(ex);
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    public static <T extends AutoCloseable> void closeAllWithChain(ArrayList<T> elements) throws Exception {
        Exception exception = null;
        for (T el : elements){
            try{
                el.close();
            }catch (Exception ex){
                if (exception == null){
                    exception = new Exception(ex.getMessage(), ex);
                    continue;
                }
                exception = new Exception(ex.getMessage(), exception);
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    public static <T extends AutoCloseable> void closeAllWithChainAndReflection(ArrayList<T> elements) throws Exception {
        Exception exception = null;
        for (T el : elements){
            try{
                el.close();
            }catch (Exception ex){
                if (exception == null){
                    exception = ex;
                    continue;
                }
                ex.initCause(exception);
                exception = ex;
                /*
                Class<? extends Exception> clazz = ex.getClass();
                try {
                    exception = clazz.getConstructor(String.class, Throwable.class).newInstance(ex.getMessage(), exception);
                }catch (Exception exRef){
                    exception = new Exception(clazz.getCanonicalName() + ": " + ex.getMessage(), exception);
                }
                */
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    public static void main(String[] args) throws Exception {
        ArrayList<AutoCloseable> mr = new ArrayList<>();
        mr.add(new MyResource(1));
        mr.add(new MyResource(2));
        mr.add(new MyResourceNew(3));
        mr.add(new MyResource(4));
        mr.add(new MyResourceNew(5));
        //closeAll(mr);
        //closeAllWithChain(mr);
        closeAllWithChainAndReflection(mr);
    }
}
