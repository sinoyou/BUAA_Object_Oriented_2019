package component;

import com.oocourse.uml1.models.elements.UmlInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class InterfaceNode extends ClassInterfaceModel {
    private ArrayList<InterfaceNode> generateList;

    public InterfaceNode(UmlInterface umlInterface) {
        super(umlInterface);
        generateList = new ArrayList<>();
    }

    /* -------- New Implemented Method*/
    public void addGenerateFrom(InterfaceNode interfaceNode) {
        generateList.add(interfaceNode);
    }

    public Iterator<InterfaceNode> getGenerateListInterator() {
        return getGenerateList().iterator();
    }

    /* ------- Inner Help Function -------- */

    /**
     * Use BFS to search all generated interfaces of 'this'(Including itself).
     * HINT: Generation relation of interfaces can be more complex than class.
     *
     * @return Set of InterfaceNode Objects.(No duplicated objects!)
     */
    private HashSet<InterfaceNode> getGenerateList() {
        HashSet<InterfaceNode> set = new HashSet<>();
        LinkedList<InterfaceNode> queue = new LinkedList<>();
        set.add(this);
        queue.addLast(this);
        while (!queue.isEmpty()) {
            InterfaceNode nodeFrom = queue.removeFirst();
            for (InterfaceNode nodeTo : nodeFrom.generateList) {
                if (!set.contains(nodeTo)) {
                    set.add(nodeTo);
                    queue.addLast(nodeTo);
                }
            }
        }
        return set;
    }
}
