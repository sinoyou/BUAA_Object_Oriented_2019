package method;

import item.Item;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return (o2.getCoe().compareTo(o1.getCoe()));
    }
}
