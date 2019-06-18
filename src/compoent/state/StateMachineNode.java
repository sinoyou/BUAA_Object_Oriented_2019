package compoent.state;

import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import compoent.NodeModel;

public class StateMachineNode implements NodeModel {
    private UmlElement kernelInstance;
    private RegionNode regionNode;

    public StateMachineNode(UmlElement umlElement) {
        if (umlElement == null) {
            System.err.println("[StateMachine]:Null");
        } else if (umlElement instanceof UmlStateMachine) {
            this.kernelInstance = umlElement;
            regionNode = null;
        } else {
            System.err.println(String.format("[StateMachine]:Wrong " +
                    "Type of Kernel UML Element %s %s",
                umlElement.getElementType(), umlElement.getName()));
        }
    }

    @Override
    public UmlElement getKernelInstance() {
        return kernelInstance;
    }

    /* -------- Modification Method -------- */
    public void addOneRegion(RegionNode regionNode) {
        if (this.regionNode == null) {
            this.regionNode = regionNode;
        } else {
            System.err.println("[StateMachine]:Duplicated region node.");
        }
    }

    /* -------- Query Method -------- */
    public int getStateCount() {
        return regionNode.getStateCount();
    }

    public int getTransitionCount() {
        return regionNode.getTransitionCount();
    }

    /**
     * Exception parameters must be redefined to meet requirement.
     * @param name name of state
     * @return sub sequence count of the state
     * @throws StateDuplicatedException multiple states shared same name
     * @throws StateNotFoundException no state with name given
     */
    public int getSubsequentStateCount(String name)
        throws StateDuplicatedException, StateNotFoundException {
        try {
            return regionNode.getSubStateCount(name);
        } catch (StateDuplicatedException e1) {
            throw new StateDuplicatedException(kernelInstance.getName(), name);
        } catch (StateNotFoundException e2) {
            throw new StateNotFoundException(kernelInstance.getName(), name);
        }
    }
}
