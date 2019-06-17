package compoent.state;

import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlTransition;
import compoent.NodeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RegionNode implements NodeModel {
    private UmlElement kernelInstance;
    private HashMap<String, List<StateNode>> nameToState;
    private HashMap<String, StateNode> idToState;
    private List<UmlTransition> transitionList;

    public RegionNode(UmlElement umlElement) {
        if (umlElement == null) {
            System.err.println("[Region]:null");
        } else if (umlElement instanceof UmlRegion) {
            kernelInstance = umlElement;
            nameToState = new HashMap<>();
            idToState = new HashMap<>();
            transitionList = new ArrayList<>();
        } else {
            System.err.println(String.format("[Region]:Wrong " +
                    "Type of Kernel UML Element %s %s",
                umlElement.getElementType(), umlElement.getName()));
        }
    }

    @Override
    public UmlElement getKernelInstance() {
        return kernelInstance;
    }

    /* -------- Modification Method -------- */

    /**
     * Add one state node to region.
     * Caution : when build name mapping, pre-require is name not null.
     * @param stateNode
     */
    public void addOneState(StateNode stateNode) {
        // id to state node
        idToState.put(stateNode.getKernelInstance().getId(), stateNode);
        // name to state node
        if (stateNode.getKernelInstance().getName() != null) {
            if (!nameToState.containsKey(stateNode.getKernelInstance().getName())) {
                nameToState.put(stateNode.getKernelInstance().getName(),
                    new ArrayList<>());
            }
            nameToState.get(stateNode.getKernelInstance().getName()).add(stateNode);
        }
    }

    public void addOneTransition(UmlTransition umlTransition) {
        transitionList.add(umlTransition);
        String sourceId = umlTransition.getSource();
        String targetId = umlTransition.getTarget();
        if (idToState.containsKey(sourceId) && idToState.containsKey(targetId)) {
            idToState.get(sourceId).addOneDirectSubState(
                idToState.get(targetId));
        } else {
            System.err.println(String.format("[Region]:Unknown src | dst of transition %s", umlTransition.getId()));
        }
    }

    /* -------- Query Method -------- */
    public int getStateCount() {
        return idToState.size();
    }

    public int getTransitionCount() {
        return transitionList.size();
    }

    public int getSubStateCount(String name) throws StateDuplicatedException, StateNotFoundException {
        List<StateNode> list = nameToState.getOrDefault(name, null);
        StateNode srcNode = null;
        if (list != null) {
            if (list.size() == 1) {
                srcNode = list.get(0);
            } else {
                throw new StateDuplicatedException(kernelInstance.getName(), name);
            }
        } else {
            throw new StateNotFoundException(kernelInstance.getName(), name);
        }

        // count : with bfs
        HashSet<StateNode> set = new HashSet<>();
        LinkedList<StateNode> queue = new LinkedList<>();
        set.add(srcNode);
        queue.addLast(srcNode);

        while (!queue.isEmpty()) {
            StateNode node = queue.removeFirst();
            Iterator<StateNode> it = node.getDirectSubState();
            while (it.hasNext()) {
                StateNode toNode = it.next();
                if (!set.contains(toNode)) {
                    set.add(toNode);
                    queue.addLast(toNode);
                }
            }
        }

        return set.size();
    }
}