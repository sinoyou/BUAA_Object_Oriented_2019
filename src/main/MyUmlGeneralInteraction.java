package main;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.PreCheckRuleException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlElement;
import compoent.interaction.InteractionNode;
import compoent.model.ClassNode;
import compoent.model.InterfaceNode;
import compoent.state.StateMachineNode;
import handler.AddElementHandler;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {

    // Get navigator help class
    private IdToUmlElement idMap = IdToUmlElement.getInstance();
    private NodeNavigator nodeNav = NodeNavigator.getInstance();

    public MyUmlGeneralInteraction(UmlElement[] elements) {

        // Transform elements array to arrayList
        ArrayList<UmlElement> elementList = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            UmlElement umlElement = elements[i];
            elementList.add(umlElement);
            idMap.addUmlElement(umlElement);
        }

        // todo sort by type

        AddElementHandler.addHandler(elementList);
    }

    /* >>>>>>>> UML compoent.model Query <<<<<<<< */
    @Override
    public int getClassCount() {
        return nodeNav.getClassCount();
    }

    /* -------- ClassNode Inner Data Analysis -------- */
    @Override
    public int getClassOperationCount(String s,
                                      OperationQueryType operationQueryType)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        return classNode.getSelfOperationCount(operationQueryType);
    }

    @Override
    public int getClassAttributeCount(String s,
                                      AttributeQueryType attributeQueryType)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        if (attributeQueryType == AttributeQueryType.SELF_ONLY) {
            return classNode.getSelfAttributesCount();
        } else {
            int count = 0;
            Iterator<ClassNode> classIt = classNode.getGenerateListIterator();
            while (classIt.hasNext()) {
                count += classIt.next().getSelfAttributesCount();
            }
            return count;
        }
    }

    @Override
    public int getClassAssociationCount(String s)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        Iterator<ClassNode> classGenerateIt =
            classNode.getGenerateListIterator();
        int count = 0;
        while (classGenerateIt.hasNext()) {
            count += classGenerateIt.next().getSelfAssociationAmount();
        }
        return count;
    }

    @Override
    public List<String> getClassAssociatedClassList(String s)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        Iterator<ClassNode> generateIt = classNode.getGenerateListIterator();

        // Initial a hash set to save all association end which is class
        HashSet<ClassNode> set = new HashSet<>();
        while (generateIt.hasNext()) {
            ClassNode node = generateIt.next();
            set.addAll(node.getSelfAssociatedClasses());
        }

        // form name list
        ArrayList<String> nameList = new ArrayList<>();
        for (ClassNode node : set) {
            nameList.add(node.getKernelInstance().getName());
        }
        return nameList;
    }

    /* -------- Visibility Analysis -------- */
    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(String s,
                                                                String s1)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        return classNode.getSelfOperationVisibility(s1);
    }

    @Override
    public Visibility getClassAttributeVisibility(String s, String s1)
        throws ClassNotFoundException, ClassDuplicatedException,
        AttributeNotFoundException, AttributeDuplicatedException {

        ClassNode classNode = nodeNav.getClassNodeByName(s);
        Iterator<ClassNode> classGenerateIt =
            classNode.getGenerateListIterator();
        Visibility visibility = null;
        while (classGenerateIt.hasNext()) {
            try {
                Visibility temp =
                    classGenerateIt.next().getSelfAttributeVisibility(s1);
                if (visibility != null && temp != null) {
                    throw new AttributeDuplicatedException(s, s1);
                }
                visibility = temp;
            } catch (AttributeNotFoundException e1) {
                // ignore
            } catch (AttributeDuplicatedException e2) {
                throw new AttributeDuplicatedException(s, s1);
            }
        }

        if (visibility == null) {
            throw new AttributeNotFoundException(s, s1);
        } else {
            return visibility;
        }
    }


    /* -------- Other -------- */
    @Override
    public String getTopParentClass(String s)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        return classNode.getTopClass().getKernelInstance().getName();
    }

    @Override
    public List<String> getImplementInterfaceList(String s)
        throws ClassNotFoundException, ClassDuplicatedException {
        ClassNode classNode = nodeNav.getClassNodeByName(s);
        // initial a set to save interfaceNode
        // loop1 : loop for generation of class
        // loop2 : loop for realized interfaces of class.
        // loop3 : loop for generation of interface realized by class
        HashSet<InterfaceNode> set = new HashSet<>();
        Iterator<ClassNode> classGenerateIt =
            classNode.getGenerateListIterator();
        while (classGenerateIt.hasNext()) {
            Iterator<InterfaceNode> interfaceIt =
                classGenerateIt.next().getSelfImplementInterface();
            while (interfaceIt.hasNext()) {
                Iterator<InterfaceNode> interfaceGenerateIt =
                    interfaceIt.next().getGenerateListIterator();
                while (interfaceGenerateIt.hasNext()) {
                    set.add(interfaceGenerateIt.next());
                }
            }
        }

        // form name list
        ArrayList<String> nameList = new ArrayList<>();
        for (InterfaceNode interfaceNode : set) {
            nameList.add(interfaceNode.getKernelInstance().getName());
        }
        return nameList;
    }

    @Override
    public List<AttributeClassInformation> getInformationNotHidden(String s)
        throws ClassNotFoundException, ClassDuplicatedException {

        ClassNode classNode = nodeNav.getClassNodeByName(s);
        ArrayList<AttributeClassInformation> list = new ArrayList<>();
        Iterator<ClassNode> classGenerateIt =
            classNode.getGenerateListIterator();
        while (classGenerateIt.hasNext()) {
            list.addAll(classGenerateIt.next().getSelfNotHiddenAttribute());
        }
        return list;
    }

    /* >>>>>>>> UML Sequence Query <<<<<<<< */

    @Override
    public int getParticipantCount(String s) throws InteractionNotFoundException, InteractionDuplicatedException {
        InteractionNode interactionNode = nodeNav.getInteractionNodeByName(s);
        return interactionNode.getParticipantCount();
    }

    @Override
    public int getMessageCount(String s) throws InteractionNotFoundException, InteractionDuplicatedException {
        InteractionNode interactionNode = nodeNav.getInteractionNodeByName(s);
        return interactionNode.getMessageCount();
    }

    @Override
    public int getIncomingMessageCount(String s, String s1) throws InteractionNotFoundException, InteractionDuplicatedException, LifelineNotFoundException, LifelineDuplicatedException {
        InteractionNode interactionNode = nodeNav.getInteractionNodeByName(s);
        return interactionNode.getIncomingMessageCount(s1);
    }

    /* >>>>>>>> UML State Query <<<<<<<< */

    @Override
    public int getStateCount(String s) throws StateMachineNotFoundException, StateMachineDuplicatedException {
        StateMachineNode stateMachineNode = nodeNav.getStateMachineNodeByName(s);
        return stateMachineNode.getStateCount();
    }

    @Override
    public int getTransitionCount(String s) throws StateMachineNotFoundException, StateMachineDuplicatedException {
        StateMachineNode stateMachineNode = nodeNav.getStateMachineNodeByName(s);
        return stateMachineNode.getStateCount();
    }

    @Override
    public int getSubsequentStateCount(String s, String s1) throws StateMachineNotFoundException, StateMachineDuplicatedException, StateNotFoundException, StateDuplicatedException {
        StateMachineNode stateMachineNode = nodeNav.getStateMachineNodeByName(s);
        return stateMachineNode.getSubsequentStateCount(s1);
    }

    /* >>>>>>>> UML Pre Check <<<<<<<< */

    @Override
    public void checkForAllRules() throws PreCheckRuleException {

    }

    @Override
    public void checkForUml002() throws UmlRule002Exception {

    }

    @Override
    public void checkForUml008() throws UmlRule008Exception {

    }

    @Override
    public void checkForUml009() throws UmlRule009Exception {

    }
}

