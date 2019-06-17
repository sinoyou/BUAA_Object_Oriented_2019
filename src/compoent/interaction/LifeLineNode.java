package compoent.interaction;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;
import compoent.NodeModel;

import java.util.List;

public class LifeLineNode implements NodeModel {
    private UmlElement kernelInstance;
    private List<UmlMessage> incomingMessage;

    public LifeLineNode(UmlElement umlElement) {
        if (umlElement == null) {
            System.err.println("[Lifeline]:null");
        } else if (umlElement instanceof UmlLifeline) {
            kernelInstance = umlElement;
        } else {
            System.err.println(String.format("[Lifeline]:Wrong " +
                    "Type of Kernel UML Element %s %s",
                umlElement.getElementType(), umlElement.getName()));
        }
    }

    @Override
    public UmlElement getKernelInstance() {
        return kernelInstance;
    }

    /* -------- Modification Method -------- */
    public void addIncomingMessage(UmlMessage umlMessage) {
        incomingMessage.add(umlMessage);
    }

    /* -------- Query Method -------- */
    public int getIncomingMessageCount() {
        return incomingMessage.size();
    }
}
