package task22;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class Task22 {

    public static <V, T extends Throwable> V doWork(Callable<V> c, T ex) throws T {
        try {
            return c.call ();
        } catch (Throwable realEx) {
            ex.initCause(realEx);
            throw ex;
        }
    }

    public static <V, T extends Throwable> V doWork(Callable<V> c, Class<T> classEx)
            throws T, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            return c.call ();
        } catch (Throwable realEx) {
            T ex = classEx.getConstructor(String.class).newInstance("Something...");
            ex.initCause(realEx);
            throw ex;
        }
    }
}
