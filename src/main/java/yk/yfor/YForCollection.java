package yk.yfor;

import java.util.Collection;
import java.util.Iterator;

/**
 * 31.05.2024
 */
public class YForCollection<T> implements YFor<T> {
    public Collection c;
    private Iterator itr;

    public YForCollection(Collection c) {
        this.c = c;
        itr = c.iterator();
    }

    @Override
    public void next(YForResult result) {
        if (itr.hasNext()) result.setPresent(itr.next());
        else result.setAbsent();
    }
}
