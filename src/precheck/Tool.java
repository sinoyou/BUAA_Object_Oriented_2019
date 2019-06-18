package precheck;

import compoent.model.ClassInterfaceModel;
import compoent.model.ClassNode;
import compoent.model.InterfaceNode;
import navigate.NodeNavigator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Tool {
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static Iterator<ClassInterfaceModel> getClassOrInterface() {
        HashSet<ClassInterfaceModel> set = new HashSet<>();

        // class nodes
        Iterator<ClassNode> classIt = nodeNav.getClassNodes();
        while (classIt.hasNext()) {
            set.add(classIt.next());
        }
        // interface nodes
        Iterator<InterfaceNode> interfaceIt = nodeNav.getInerfaces();
        while (interfaceIt.hasNext()) {
            set.add(interfaceIt.next());
        }

        return set.iterator();
    }

    /**
     * Given a node (with class or interface type), return iterator the node
     * generates from and implements from.
     *
     * @param node source node
     * @return iterator
     */
    public static Iterator<ClassInterfaceModel> getGenerateOrImplement(
        ClassInterfaceModel node
    ) {
        ArrayList<ClassInterfaceModel> list = new ArrayList<>();

        if (node instanceof ClassNode) {
            // generation classes
            Iterator<ClassNode> classIt = ((ClassNode) node).getGenerateFrom();
            while (classIt.hasNext()) {
                list.add(classIt.next());
            }
            // implemented interfaces
            Iterator<InterfaceNode> interfaceIt =
                ((ClassNode) node).getSelfImplementInterface();
            while (interfaceIt.hasNext()) {
                list.add(interfaceIt.next());
            }
        } else if (node instanceof InterfaceNode) {
            Iterator<InterfaceNode> interfaceIt =
                ((InterfaceNode) node).getGenerateFrom();
            // generation interfaces
            while (interfaceIt.hasNext()) {
                list.add(interfaceIt.next());
            }
        } else {
            System.err.println(String.format(
                "[uml008]:Wrong type when getting generation or implements %s",
                node.getKernelInstance().getName()));
        }

        return list.iterator();
    }
}
