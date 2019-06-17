package handler;

import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlEndpoint;
import com.oocourse.uml2.models.elements.UmlEvent;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;
import com.oocourse.uml2.models.elements.UmlOpaqueBehavior;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import com.oocourse.uml2.models.elements.UmlTransition;
import compoent.interaction.InteractionNode;
import compoent.interaction.LifeLineNode;
import compoent.model.ClassNode;
import compoent.model.InterfaceNode;
import compoent.model.OperationNode;
import compoent.state.RegionNode;
import compoent.state.StateMachineNode;
import compoent.state.StateNode;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;

public class GeneralHandler {
    private static IdToUmlElement idMap = IdToUmlElement.getInstance();
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static void handleElement(UmlElement element) {
        // Uml model
        if (element instanceof UmlAssociation) {
            handleAssociation((UmlAssociation) element);
        } else if (element instanceof UmlAssociationEnd) {
            handleAssociationEnd((UmlAssociationEnd) element);
        } else if (element instanceof UmlAttribute) {
            handleAttribute((UmlAttribute) element);
        } else if (element instanceof UmlClass) {
            handleClass((UmlClass) element);
        } else if (element instanceof UmlGeneralization) {
            handleGeneration((UmlGeneralization) element);
        } else if (element instanceof UmlInterface) {
            handleInterface((UmlInterface) element);
        } else if (element instanceof UmlInterfaceRealization) {
            handleInterfaceRealization((UmlInterfaceRealization) element);
        } else if (element instanceof UmlOperation) {
            handleOperation((UmlOperation) element);
        } else if (element instanceof UmlParameter) {
            handleParameter((UmlParameter) element);
        }
        // Uml State Machine
        else if (element instanceof UmlEvent) {
            handleEvent((UmlEvent) element);
        } else if (element instanceof UmlFinalState) {
            handleState((UmlFinalState) element);
        } else if (element instanceof UmlPseudostate) {
            handleState((UmlPseudostate) element);
        } else if (element instanceof UmlRegion) {
            handleRegion((UmlRegion) element);
        } else if (element instanceof UmlState) {
            handleState((UmlState) element);
        } else if (element instanceof UmlStateMachine) {
            handleStateMachine((UmlStateMachine) element);
        } else if (element instanceof UmlTransition) {
            handleTransition((UmlTransition) element);
        } else if (element instanceof UmlOpaqueBehavior) {
            handleOpaque((UmlOpaqueBehavior) element);
        }
        // Uml Collaboration
        else if (element instanceof UmlInteraction) {
            handleInteraction((UmlInteraction) element);
        } else if (element instanceof UmlEndpoint) {
            handleEndpoint((UmlEndpoint) element);
        } else if (element instanceof UmlLifeline) {
            handleLifeline((UmlLifeline) element);
        } else if (element instanceof UmlMessage) {
            handleMessage((UmlMessage) element);
        } else {
            System.err.println(String.format("[Handler] Unknown UmlElement" +
                " Type %s %s.", element.getElementType(), element.getId()));
        }
    }

    // >>>>>>>> UML Model <<<<<<<<
    private static void handleAssociation(UmlAssociation umlAssociation) {
        assert idMap.containsElement(umlAssociation.getId());
        // nothing need to do now
    }

    private static void handleAssociationEnd(UmlAssociationEnd
                                                 umlAssociationEnd) {
        String refId = umlAssociationEnd.getReference();
        if (nodeNav.containsClassNode(refId)) {
            ClassNode classNode = nodeNav.getClassNodeById(refId);
            classNode.addAssociateEnd(umlAssociationEnd);
        } else if (nodeNav.containsInterfaceNode(refId)) {
            InterfaceNode interfaceNode = nodeNav.getInterfaceNodeById(refId);
            interfaceNode.addAssociateEnd(umlAssociationEnd);
        } else {
            printNotMatch("AssoEnd", umlAssociationEnd, "class|interface");
        }
    }

    private static void handleAttribute(UmlAttribute umlAttribute) {
        String parentId = umlAttribute.getParentId();
        if (nodeNav.containsClassNode(parentId)) {
            ClassNode classNode = nodeNav.getClassNodeById(parentId);
            classNode.addAttribute(umlAttribute);
        } else if (nodeNav.containsInterfaceNode(parentId)) {
            InterfaceNode interfaceNode =
                nodeNav.getInterfaceNodeById(parentId);
            interfaceNode.addAttribute(umlAttribute);
        } else if (nodeNav.containsStateMachineNode(parentId)) {
            printIgnore("Attribute", umlAttribute);
        } else {
            printNotMatch("Attribute", umlAttribute, "class|interface");
        }
    }

    private static void handleClass(UmlClass umlClass) {
        ClassNode classNode = new ClassNode(umlClass);
        nodeNav.addOneClassNode(classNode);
    }

    private static void handleGeneration(UmlGeneralization umlGeneralization) {
        String source = umlGeneralization.getSource();
        String target = umlGeneralization.getTarget();
        // class type generation
        if (nodeNav.containsClassNode(source)) {
            assert nodeNav.containsClassNode(target);
            ClassNode classNodeSrc = nodeNav.getClassNodeById(source);
            ClassNode classNodeDst = nodeNav.getClassNodeById(target);
            classNodeSrc.addGenerateFrom(classNodeDst);
        }
        // interface type generation
        else if (nodeNav.containsInterfaceNode(source)) {
            assert nodeNav.containsInterfaceNode(target);
            InterfaceNode interfaceNodeSrc =
                nodeNav.getInterfaceNodeById(source);
            InterfaceNode interfaceNodeDst =
                nodeNav.getInterfaceNodeById(target);
            interfaceNodeSrc.addGenerateFrom(interfaceNodeDst);
        } else {
            printError("HandleGeneration", "Unknown Generation Type", umlGeneralization);
        }
    }

    private static void handleInterface(UmlInterface umlInterface) {
        InterfaceNode interfaceNode = new InterfaceNode(umlInterface);
        nodeNav.addOneInterfaceNode(interfaceNode);
    }

    private static void handleInterfaceRealization(
        UmlInterfaceRealization umlInterfaceRealization) {
        String source = umlInterfaceRealization.getSource();
        String target = umlInterfaceRealization.getTarget();
        // Get interface be realized
        InterfaceNode interfaceNode;
        assert nodeNav.containsInterfaceNode(target);
        if (nodeNav.containsInterfaceNode(target)) {
            interfaceNode = nodeNav.getInterfaceNodeById(target);
        } else {
            printError("HandleRealization", "interface not found", umlInterfaceRealization);
            return;
        }
        ClassNode classNode;
        assert nodeNav.containsClassNode(source);
        if (nodeNav.containsClassNode(source)) {
            classNode = nodeNav.getClassNodeById(source);
        } else {
            System.err.println(String.format("[Handler] Error, class " +
                "should exist in realization %s", target));
            printError("HandleRealization", "class not found", umlInterfaceRealization);
            return;
        }
        classNode.addRealize(interfaceNode);
    }

    private static void handleOperation(UmlOperation umlOperation) {
        OperationNode operationNode = new OperationNode(umlOperation);
        nodeNav.addOneOperation(operationNode);

        String parentId = operationNode.getKernelInstance().getParentId();
        if (nodeNav.containsClassNode(parentId)) {
            nodeNav.getClassNodeById(parentId).addOperation(operationNode);
        } else if (nodeNav.containsInterfaceNode(parentId)) {
            nodeNav.getInterfaceNodeById(parentId).
                addOperation(operationNode);
        } else {
            printNotMatch("HandleOperation", umlOperation, "class|interface");
        }
    }

    private static void handleParameter(UmlParameter umlParameter) {
        String opId = umlParameter.getParentId();
        assert nodeNav.containsOperationNode(opId);
        if (nodeNav.containsOperationNode(opId)) {
            OperationNode operationNode = nodeNav.getOperationNodeById(opId);
            operationNode.addParameter(umlParameter);
        } else {
            printError("HandleParameter", "Operation not found", umlParameter);
        }
    }

    // >>>>>>>> UML State Machine <<<<<<<<
    private static void handleEvent(UmlEvent umlEvent) {
        printIgnore("handleEvent", umlEvent);
    }

    private static void handleState(UmlElement umlStateElement) {
        StateNode stateNode = new StateNode(umlStateElement);
        // add to parent's node
        String pid = umlStateElement.getParentId();
        if (nodeNav.containsRegionNode(pid)) {
            nodeNav.getRegionNodeById(pid).addOneState(stateNode);
        } else {
            printError("handleState", "region not found", umlStateElement);
        }
    }

    private static void handleRegion(UmlRegion umlRegion) {
        RegionNode regionNode = new RegionNode(umlRegion);
        // add to nodeNav
        nodeNav.addOneRegionNode(regionNode);
        // add to parent's node
        String pid = umlRegion.getParentId();
        if (nodeNav.containsStateMachineNode(pid)) {
            nodeNav.getStateMachineNodeById(pid).addOneRegion(regionNode);
        } else {
            printError("handleRegion", "State Machine not found", umlRegion);
        }
    }

    private static void handleStateMachine(UmlStateMachine umlStateMachine) {
        StateMachineNode stateMachineNode = new StateMachineNode(umlStateMachine);
        // add to nodeNav
        nodeNav.addOneStateMachine(stateMachineNode);
    }

    private static void handleTransition(UmlTransition umlTransition) {
        String pid = umlTransition.getParentId();
        if (nodeNav.containsRegionNode(pid)) {
            nodeNav.getRegionNodeById(pid).addOneTransition(umlTransition);
        } else {
            printError("handleTransition", "Region not found", umlTransition);
        }
    }

    private static void handleOpaque(UmlOpaqueBehavior umlOpaqueBehavior) {
        printIgnore("handleOpaque", umlOpaqueBehavior);
    }

    // >>>>>>>> UML Collaboration <<<<<<<<
    private static void handleInteraction(UmlInteraction umlInteraction) {
        // add to nodeNav
        InteractionNode interactionNode = new InteractionNode(umlInteraction);
        nodeNav.addOneInteractionNode(interactionNode);
    }

    private static void handleEndpoint(UmlEndpoint umlEndpoint) {
        printIgnore("handleEndPoint", umlEndpoint);
    }

    private static void handleLifeline(UmlLifeline umlLifeline) {
        String pid = umlLifeline.getParentId();
        LifeLineNode lifeLineNode = new LifeLineNode(umlLifeline);
        // add to parent's node
        if (nodeNav.containsInteractionNode(pid)) {
            nodeNav.getInteractionNodeById(pid).addOneLifeline(lifeLineNode);
        } else {
            printError("handleLifeline", "Interaction not found", umlLifeline);
        }
    }

    private static void handleMessage(UmlMessage umlMessage) {
        String pid = umlMessage.getParentId();
        // add to parent's node
        if (nodeNav.containsInteractionNode(pid)) {
            nodeNav.getInteractionNodeById(pid).addOneMessage(umlMessage);
        } else {
            printError("handleMessage", "Interaction not found", umlMessage);
        }
    }

    // System Error Print
    private static void printNotMatch(String who, UmlElement element, String expect) {
        String s = String.format("[%s] type %s not referred to %s %s", who, element.getElementType(), expect, element.getId());
        System.err.println(s);
    }

    private static void printError(String who, String message, UmlElement umlElement) {
        String s = String.format("[%s] %s %s", who, message, umlElement.getId());
        System.err.println(s);
    }

    private static void printIgnore(String who, UmlElement element) {
        String s = String.format("@%s type %s is ignored %s",
            who, element.getElementType(), element.getId());
        System.err.println(s);
    }
}
