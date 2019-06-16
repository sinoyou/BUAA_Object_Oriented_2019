package handler;

import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import component.ClassNode;
import component.InterfaceNode;
import component.OperationNode;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;

public class AddElementHandler {
    private static IdToUmlElement idMap = IdToUmlElement.getInstance();
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static void handleElement(UmlElement element) {
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
        } else {
            System.err.println(String.format("[Handler] Unknown UmlElement" +
                " Type %s %s.", element.getElementType(), element.getId()));
        }
    }

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
            System.err.println(String.format("[Handler]:Error, End " +
                "refered to class or interface %s", umlAssociationEnd.getId()));
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
        } else {
            System.err.println(String.format("[Handler]:Error, attr is " +
                "not referred to class or interface %s", umlAttribute.getId()));
        }
    }

    private static void handleClass(UmlClass umlClass) {
        System.err.println(String.format("[Handler] Error, class element" +
            "should not be handled here %s ", umlClass.getId()));
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
            System.err.println(String.format("[Handler]: Error, unknown " +
                "generation type %s", umlGeneralization.getId()));
        }
    }

    private static void handleInterface(UmlInterface umlInterface) {
        System.err.println(String.format("[Handler] Error, interface element" +
            "should not be handled here %s ", umlInterface.getId()));
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
            System.err.println(String.format("[Handler] Error, interface " +
                "should exist in realization %s", target));
            return;
        }
        ClassNode classNode;
        assert nodeNav.containsClassNode(source);
        if (nodeNav.containsClassNode(source)) {
            classNode = nodeNav.getClassNodeById(source);
        } else {
            System.err.println(String.format("[Handler] Error, class " +
                "should exist in realization %s", target));
            return;
        }
        classNode.addRealize(interfaceNode);
    }

    private static void handleOperation(UmlOperation umlOperation) {
        System.err.println(String.format("[Handler] Error,oper element %s " +
            "should not be handled here.", umlOperation.getId()));
    }

    private static void handleParameter(UmlParameter umlParameter) {
        String opId = umlParameter.getParentId();
        assert nodeNav.containsOperationNode(opId);
        if (nodeNav.containsOperationNode(opId)) {
            OperationNode operationNode = nodeNav.getOperationNodeById(opId);
            operationNode.addParameter(umlParameter);
        } else {
            System.err.println(String.format("[Handler] Error,para %s's op" +
                "should exist %s", umlParameter.getName(), opId));
        }
    }
}
