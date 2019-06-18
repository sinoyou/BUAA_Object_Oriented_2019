package compoent.interaction;

import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlMessage;
import compoent.NodeModel;

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
     *
     * @param lifeLineNode life line to be added
     */
    public void addOneLifeline(LifeLineNode lifeLineNode) {
        String id = lifeLineNode.getKernelInstance().getId();
        String name = lifeLineNode.getKernelInstance().getName();
        // id add
        idToLifeline.put(id, lifeLineNode);
        // name add
        if (name != null) {
            if (!nameToLifeline.containsKey(name)) {
                nameToLifeline.put(name, new ArrayList<>());
            }
            nameToLifeline.get(name).add(lifeLineNode);
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
            System.err.println(String.format("" +
                "[Interaction]:Unknown dst of message %s", umlMessage.getId()));
        }
    }

    /* -------- Query Method -------- */
    public int getMessageCount() {
        return messageList.size();
    }

    public int getParticipantCount() {
        return idToLifeline.size();
    }

    public int getIncomingMessageCount(String name)
        throws LifelineNotFoundException, LifelineDuplicatedException {
        List<LifeLineNode> list = nameToLifeline.getOrDefault(name, null);
        String interactionName = kernelInstance.getName();
        if (list == null) {
            throw new LifelineNotFoundException(interactionName, name);
        } else if (list.size() > 1) {
            throw new LifelineDuplicatedException(interactionName, name);
        } else {
            return list.get(0).getIncomingMessageCount();
        }
    }
}
