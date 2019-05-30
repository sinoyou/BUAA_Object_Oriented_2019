package component;

import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ClassNode extends ClassInterfaceModel {
    private ArrayList<InterfaceNode> realizeList;
    private ClassNode generateFrom;


    public ClassNode(UmlClass umlClass) {
        super(umlClass);
        generateFrom = null;
        realizeList = new ArrayList<>();
    }

    @Override
    public UmlClass getKernelInstance() {
        return (UmlClass) super.getKernelInstance();
    }

    /* -------- New Implemented Method -------- */
    public void addRealize(InterfaceNode interfaceNode) {
        realizeList.add(interfaceNode);
    }

    public Iterator<InterfaceNode> getSelfImplementInterface() {
        return realizeList.iterator();
    }

    public void addGenerateFrom(ClassNode classNode) {
        generateFrom = classNode;
    }

    public Iterator<ClassNode> getGenerateListIterator() {
        return getGenerateList().iterator();
    }

    public ClassNode getTopClass() {
        LinkedList<ClassNode> list = getGenerateList();
        return list.getLast();
    }

    /* -------- Inner Help Function -------- */

    /**
     * Form a list of generation class of this class. (Including itself)
     * HINT: Generation relation between classes are simple, just single and
     * linear generation.
     *
     * @return A list of generation (head -> this class, tail -> top class)
     */
    private LinkedList<ClassNode> getGenerateList() {
        LinkedList<ClassNode> linkedList = new LinkedList<>();
        ClassNode p = this;
        while (p != null) {
            linkedList.addLast(p);
            p = p.generateFrom;
        }
        return linkedList;
    }
}
