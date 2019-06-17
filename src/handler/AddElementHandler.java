package handler;

import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlOperation;
import compoent.model.ClassNode;
import compoent.model.InterfaceNode;
import compoent.model.OperationNode;
import navigate.NodeNavigator;

import java.util.Iterator;
import java.util.List;

public class AddElementHandler {
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static void addHandler(List<UmlElement> elementList) {

        // Step 1: Process Top Element : Class, Interface,
        // State Machine & Region, Interaction
        handleTopElement(elementList);

        // Step 2: Process Middle Element : Operation, State, Lifeline
        // Caution: Middle layer elements are bridge between top and bottom,
        // it may be added back to top after proceeding bottom.
        handleMiddleElement(elementList);

        // Step 3: General Handle
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            GeneralHandler.handleElement(it.next());
        }

        // Step 4: Process Middle Element Backwards
        handlerMiddleElementBackwards();
    }

    /**
     * To handler Top uml elements to prepare other elements' container.
     * 1. Class Node
     * 2. Interface Node
     * 3. State Machine Node (in fact is Region)
     * 4. Interaction Node
     *
     * @param elementList
     */
    private static void handleTopElement(List<UmlElement> elementList) {
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlClass) {
                it.remove();
                ClassNode classNode = new ClassNode((UmlClass) element);
                nodeNav.addOneClassNode(classNode);
            } else if (element instanceof UmlInterface) {
                it.remove();
                InterfaceNode interfaceNode = new InterfaceNode((UmlInterface)
                    element);
                nodeNav.addOneInterfaceNode(interfaceNode);
            }
        }
    }

    /**
     * Middle elements are bridge between top and bottom, it has to be
     * proceeded twice. This time is to make preparation for link bottom.
     * 1. Operation Node
     * 2. State Node
     * 3. LifeLine Node
     *
     * @param elementList Elements remains to be proceeded.
     */
    private static void handleMiddleElement(List<UmlElement> elementList) {
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlOperation) {
                it.remove();
                OperationNode operationNode = new OperationNode(
                    (UmlOperation) element);
                nodeNav.addOneOperation(operationNode);
            }
        }
    }

    /**
     * Middle elements are bridge between top and bottom,
     * it has to be proceeded twice.(last time link bottom, this time link top)
     * 1. Operation :Add Operation Node back to Class or Interface Node
     */
    private static void handlerMiddleElementBackwards() {
        Iterator<OperationNode> opIt = nodeNav.getOperationNodes();
        while (opIt.hasNext()) {
            OperationNode operationNode = opIt.next();
            String parentId = operationNode.getKernelInstance().getParentId();

            if (nodeNav.containsClassNode(parentId)) {
                nodeNav.getClassNodeById(parentId).addOperation(operationNode);
            } else if (nodeNav.containsInterfaceNode(parentId)) {
                nodeNav.getInterfaceNodeById(parentId).
                    addOperation(operationNode);
            } else {
                System.err.println(String.format("[UMLInteraction]: Error" +
                        "when add back operation %s to class or interface.",
                    operationNode.getKernelInstance().getName()));
            }
        }
    }
}
