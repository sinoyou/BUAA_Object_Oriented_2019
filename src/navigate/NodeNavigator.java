package navigate;

import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import compoent.interaction.InteractionNode;
import compoent.model.ClassNode;
import compoent.model.InterfaceNode;
import compoent.model.OperationNode;
import compoent.state.RegionNode;
import compoent.state.StateMachineNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NodeNavigator {
    // class node
    private int classCount;
    private HashMap<String, List<ClassNode>> nameToClassMap;
    private HashMap<String, ClassNode> idToClassMap;

    // interface node
    private HashMap<String, InterfaceNode> idToInterfaceMap;

    // operation node
    private HashMap<String, OperationNode> idToOperation;

    // state machine node
    private HashMap<String, StateMachineNode> idToStateMachine;
    private HashMap<String, List<StateMachineNode>> nameToStateMachine;

    // region node
    private HashMap<String, RegionNode> idToRegion;

    // interaction node
    private HashMap<String, InteractionNode> idToInteraction;
    private HashMap<String, List<InteractionNode>> nameToInteraction;

    // single instance mode
    private static NodeNavigator nodeNavigator = null;

    private NodeNavigator() {
        classCount = 0;
        nameToClassMap = new HashMap<>();
        idToClassMap = new HashMap<>();
        idToInterfaceMap = new HashMap<>();
        idToOperation = new HashMap<>();

        idToStateMachine = new HashMap<>();
        nameToStateMachine = new HashMap<>();
        idToRegion = new HashMap<>();
        idToInteraction = new HashMap<>();
        nameToInteraction = new HashMap<>();
    }

    public static NodeNavigator getInstance() {
        if (nodeNavigator == null) {
            nodeNavigator = new NodeNavigator();
        }
        return nodeNavigator;
    }

    /* -------- Class Node -------- */
    public void addOneClassNode(ClassNode classNode) {
        // count
        classCount++;
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

    public int getClassCount() {
        return classCount;
    }

    public ClassNode getClassNodeById(String id) {
        return idToClassMap.get(id);
    }

    public boolean containsClassNode(String id) {
        return idToClassMap.containsKey(id);
    }

    public Iterator<ClassNode> getClassNodes() {
        return idToClassMap.values().iterator();
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

    public Iterator<InterfaceNode> getInerfaces() {
        return idToInterfaceMap.values().iterator();
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

    /* -------- State Machine Node -------- */
    public void addOneStateMachine(StateMachineNode stateMachineNode) {
        String id = stateMachineNode.getKernelInstance().getId();
        String name = stateMachineNode.getKernelInstance().getName();
        // id map
        idToStateMachine.put(id, stateMachineNode);
        // name map
        if (!nameToStateMachine.containsKey(name)) {
            nameToStateMachine.put(name, new ArrayList<>());
        }
        nameToStateMachine.get(name).add(stateMachineNode);
    }

    public StateMachineNode getStateMachineNodeByName(String name) throws StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!nameToStateMachine.containsKey(name)) {
            throw new StateMachineNotFoundException(name);
        } else {
            List<StateMachineNode> list = nameToStateMachine.get(name);
            if (list.size() > 1) {
                throw new StateMachineDuplicatedException(name);
            } else {
                return list.get(0);
            }
        }
    }

    public StateMachineNode getStateMachineNodeById(String id) {
        return idToStateMachine.get(id);
    }

    public boolean containsStateMachineNode(String id) {
        return idToStateMachine.containsKey(id);
    }

    /* -------- Region Node -------- */
    public void addOneRegionNode(RegionNode regionNode) {
        String id = regionNode.getKernelInstance().getId();
        idToRegion.put(id, regionNode);
    }

    public RegionNode getRegionNodeById(String id) {
        return idToRegion.get(id);
    }

    public boolean containsRegionNode(String id) {
        return idToRegion.containsKey(id);
    }

    /* -------- Interaction Node -------- */
    public void addOneInteractionNode(InteractionNode interactionNode) {
        String id = interactionNode.getKernelInstance().getId();
        String name = interactionNode.getKernelInstance().getName();
        // id map
        idToInteraction.put(id, interactionNode);
        // name map
        if (!nameToInteraction.containsKey(name)) {
            nameToInteraction.put(name, new ArrayList<>());
            nameToInteraction.get(name).add(interactionNode);
        } else {
            nameToInteraction.get(name).add(interactionNode);
        }
    }

    public InteractionNode getInteractionNodeById(String id) {
        return idToInteraction.get(id);
    }

    public InteractionNode getInteractionNodeByName(String name) throws InteractionDuplicatedException, InteractionNotFoundException {
        List<InteractionNode> list = nameToInteraction.getOrDefault(name, null);
        if (list != null) {
            if (list.size() == 1) {
                return list.get(0);
            } else {
                throw new InteractionDuplicatedException(name);
            }
        } else {
            throw new InteractionNotFoundException(name);
        }
    }

    public boolean containsInteractionNode(String id) {
        return idToInteraction.containsKey(id);
    }

    /* -------- clear for unit test -------- */
    public void clearAll() {
        classCount = 0;
        nameToClassMap.clear();
        idToClassMap.clear();
        idToInterfaceMap.clear();
        idToOperation.clear();

        idToStateMachine.clear();
        nameToStateMachine.clear();
        idToRegion.clear();
        idToInteraction.clear();
        nameToStateMachine.clear();
    }
}
