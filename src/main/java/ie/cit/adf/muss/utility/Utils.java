package ie.cit.adf.muss.utility;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

    private Utils() {

    }

    public static <E> List<E> toList(Iterator<E> iterator) {
        List<E> out = new ArrayList<>();
        while (iterator.hasNext())
            out.add(iterator.next());
        return out;
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        return toList(iterable.iterator());
    }
}
