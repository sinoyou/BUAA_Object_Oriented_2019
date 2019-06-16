package navigate;

import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import component.ClassNode;
import component.InterfaceNode;
import component.OperationNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NodeNavigator {
    private HashMap<String, ArrayList<ClassNode>> nameToClassMap;
    private HashMap<String, ClassNode> idToClassMap;

    private HashMap<String, InterfaceNode> idToInterfaceMap;

    private HashMap<String, OperationNode> idToOperation;
    // single instance mode
    private static NodeNavigator nodeNavigator = null;

    private NodeNavigator() {
        nameToClassMap = new HashMap<>();
        idToClassMap = new HashMap<>();
        idToInterfaceMap = new HashMap<>();
        idToOperation = new HashMap<>();
    }

    public static NodeNavigator getInstance() {
        if (nodeNavigator == null) {
            nodeNavigator = new NodeNavigator();
        }
        return nodeNavigator;
    }

    /* -------- Class Node -------- */
    public void addOneClassNode(ClassNode classNode) {
        // name map
        String name = classNode.getKernelInstance().getName();
        if (!nameToClassMap.containsKey(name)) {
            nameToClassMap.put(name, new ArrayList<>());
        }
        nameToClassMap.get(name).add(classNode);

        // id map
        idToClassMap.put(classNode.getKernelInstance().getId(), classNode);
    }

    public ClassNode getClassNodeByName(String name)
        throws ClassDuplicatedException, ClassNotFoundException {
        if (nameToClassMap.containsKey(name)) {
            if (nameToClassMap.get(name).size() == 1) {
                return nameToClassMap.get(name).get(0);
            } else {
                throw new ClassDuplicatedException(name);
            }
        } else {
            throw new ClassNotFoundException(name);
        }
    }

    public ClassNode getClassNodeById(String id) {
        return idToClassMap.get(id);
    }

    public boolean containsClassNode(String id) {
        return idToClassMap.containsKey(id);
    }

    /* -------- Interface Node --------*/
    public void addOneInterfaceNode(InterfaceNode interfaceNode) {
        idToInterfaceMap.put(interfaceNode.getKernelInstance().getId(),
            interfaceNode);
    }

    public InterfaceNode getInterfaceNodeById(String id) {
        return idToInterfaceMap.get(id);
    }

    public boolean containsInterfaceNode(String id) {
        return idToInterfaceMap.containsKey(id);
    }

    /* -------- OperationNode Node -------- */
    public void addOneOperation(OperationNode operationNode) {
        idToOperation.put(operationNode.getKernelInstance().getId(),
            operationNode);
    }

    public OperationNode getOperationNodeById(String id) {
        return idToOperation.get(id);
    }

    public Iterator<OperationNode> getOperationNodes() {
        return idToOperation.values().iterator();
    }

    public boolean containsOperationNode(String id) {
        return idToOperation.containsKey(id);
    }

    /* -------- clear for unit test -------- */
    public void clearAll() {
        nameToClassMap.clear();
        idToClassMap.clear();
        idToInterfaceMap.clear();
        idToOperation.clear();
    }
}
