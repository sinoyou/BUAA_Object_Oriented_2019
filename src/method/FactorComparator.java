package method;

import factor.Factor;

import java.util.Comparator;

public class FactorComparator implements Comparator<Factor> {

    /**
     * 1.sort by class name.
     * 2.string compare.
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Factor o1, Factor o2) {
        if (!o1.getClass().getName().equals(o2.getClass().getName())) {
            return o1.getClass().getName().compareTo(o2.getClass().getName());
        } else {
            return o1.getBase().compareTo(o2.getBase());
        }
    }
}
