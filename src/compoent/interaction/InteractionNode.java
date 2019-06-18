package compoent.interaction;

import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlMessage;
import compoent.NodeModel;
import compoent.state.StateNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InteractionNode implements NodeModel {
    private UmlElement kernelInstance;
    private HashMap<String, LifeLineNode> idToLifeline;
    private HashMap<String, List<LifeLineNode>> nameToLifeline;
    private List<UmlMessage> messageList;

    public InteractionNode(UmlElement umlElement) {
        if (umlElement == null) {
            System.err.println("[Interaction]:null");
        } else if (umlElement instanceof UmlInteraction) {
            kernelInstance = umlElement;
            idToLifeline = new HashMap<>();
            nameToLifeline = new HashMap<>();
            messageList = new ArrayList<>();

        } else {
            System.err.println(String.format("[Interaction]:Wrong " +
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
     * Add one life line object to interaction.
     * Caution: pre-require of build name map is that name not null.
     * @param lifeLineNode life line to be added
     */
    public void addOneLifeline(LifeLineNode lifeLineNode) {
        // id add
        idToLifeline.put(lifeLineNode.getKernelInstance().getId(), lifeLineNode);
        // name add
        if (lifeLineNode.getKernelInstance().getName() != null) {
            if (!nameToLifeline.containsKey(lifeLineNode.getKernelInstance().getName())) {
                nameToLifeline.put(lifeLineNode.getKernelInstance().getName(),
                    new ArrayList<>());
                nameToLifeline.get(lifeLineNode.getKernelInstance().getName()).add(lifeLineNode);
            } else {
                nameToLifeline.get(lifeLineNode.getKernelInstance().getName()).add(lifeLineNode);
            }
        }
    }

    public void addOneMessage(UmlMessage umlMessage) {
        messageList.add(umlMessage);
        String sourceId = umlMessage.getSource();
        String targetId = umlMessage.getTarget();
        if (idToLifeline.containsKey(targetId)) {
            LifeLineNode node = idToLifeline.get(targetId);
            node.addIncomingMessage(umlMessage);
        } else {
            System.err.println(String.format("[Interaction]:Unknown dst of message %s", umlMessage.getId()));
        }
    }

    /* -------- Query Method -------- */
    public int getMessageCount() {
        return messageList.size();
    }

    public int getParticipantCount() {
        return idToLifeline.size();
    }

    public int getIncomingMessageCount(String name) throws LifelineNotFoundException, LifelineDuplicatedException {
        List<LifeLineNode> list = nameToLifeline.getOrDefault(name, null);
        if (list == null) {
            throw new LifelineNotFoundException(kernelInstance.getName(), name);
        } else if (list.size() > 1) {
            throw new LifelineDuplicatedException(kernelInstance.getName(), name);
        } else {
            return list.get(0).getIncomingMessageCount();
        }
    }
}
