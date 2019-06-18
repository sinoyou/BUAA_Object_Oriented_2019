package compoent.state;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlState;
import compoent.NodeModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class StateNode implements NodeModel {
    private UmlElement kernelInstance;
    private ArrayList<StateNode> directSubStateList;

    public StateNode(UmlElement umlElement) {
        if (umlElement == null) {
            System.err.println("[StateNode]:null");
        } else if (umlElement instanceof UmlState ||
            umlElement instanceof UmlFinalState ||
            umlElement instanceof UmlPseudostate) {
            kernelInstance = umlElement;
            directSubStateList = new ArrayList<>();
        } else {
            System.err.println(String.format("[State]:Wrong " +
                    "Type of Kernel UML Element %s %s",
                umlElement.getElementType(), umlElement.getName()));
        }
    }

    @Override
    public UmlElement getKernelInstance() {
        return kernelInstance;
    }

    /* -------- Modification Method -------- */
    public void addOneDirectSubState(StateNode stateNode) {
        directSubStateList.add(stateNode);
    }

    /* -------- Query Method -------- */
    public Iterator<StateNode> getDirectSubState() {
        return directSubStateList.iterator();
    }
}
