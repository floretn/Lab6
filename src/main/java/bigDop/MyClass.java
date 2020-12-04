package bigDop;

import com.sun.corba.se.spi.orb.ORBVersion;
import org.omg.CORBA.portable.OutputStream;

public class MyClass implements ORBVersion {
    @Override
    public byte getORBType() {
        return 0;
    }

    @Override
    public void write(OutputStream os) {

    }

    @Override
    public boolean lessThan(ORBVersion version) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
