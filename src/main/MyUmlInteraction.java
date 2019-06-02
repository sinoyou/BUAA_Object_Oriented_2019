package main;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlInterface;
import com.oocourse.uml1.models.elements.UmlOperation;
import component.ClassNode;
import component.InterfaceNode;
import component.OperationNode;
import handler.AddElementHandler;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {

    private int classCount = 0;
    // Get navigator help class
    private IdToUmlElement idMap = IdToUmlElement.getInstance();
    private NodeNavigator nodeNav = NodeNavigator.getInstance();

    public MyUmlInteraction(UmlElement[] elements) {

        // Transform elements array to arrayList
        ArrayList<UmlElement> elementList = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            UmlElement umlElement = elements[i];
            elementList.add(umlElement);
            idMap.addUmlElement(umlElement);
        }

        // Step 1: Process with Class Or Interface
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlClass) {
                it.remove();
                ClassNode classNode = new ClassNode((UmlClass) element);
                nodeNav.addOneClassNode(classNode);
                classCount++; // special info
            } else if (element instanceof UmlInterface) {
                it.remove();
                InterfaceNode interfaceNode = new InterfaceNode((UmlInterface)
                    element);
                nodeNav.addOneInterfaceNode(interfaceNode);
            }
        }

        // Step 2: Process with OperationNode
        it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlOperation) {
                it.remove();
                OperationNode operationNode = new OperationNode(
                    (UmlOperation) element);
                nodeNav.addOneOperation(operationNode);
            }
        }

        // Step 3: General Handle
        it = elementList.iterator();
        while (it.hasNext()) {
            AddElementHandler.handleElement(it.next());
        }

        // Step 4: Add Operation Node back to Class or Interface Node
        Iterator<OperationNode> opIt = nodeNav.getOperationNodes();
        while (opIt.hasNext()) {
            OperationNode operationNode = opIt.next();
            String parentId = operationNode.getKernelInstance().getParentId();

            if (nodeNav.containsClassNode(parentId)) {
                nodeNav.getClassNodeById(parentId).addOperation(operationNode);
            } else if (nodeNav.containsInterfaceNode(parentId)) {
                nodeNav.getInterfaceNodeById(parentId).
                    addOperation(operationNode);
            } else {
                System.err.println(String.format("[UMLInteraction]: Error" +
                        "when add back operation %s to class or interface.",
                    operationNode.getKernelInstance().getName()));
            }
        }
    }

    @Override
    public int getClassCount() {
        return classCount;
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

}

