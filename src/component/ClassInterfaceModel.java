package component;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlInterface;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;
import navigate.UmlAssociationMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public abstract class ClassInterfaceModel {
    private ArrayList<UmlAssociationEnd> associationEndsList;
    private ArrayList<OperationNode> operationNodeList;
    private ArrayList<UmlAttribute> attributeList;
    private UmlElement kernelInstance;

    public ClassInterfaceModel(UmlElement kernelInstance) {
        if (!(kernelInstance instanceof UmlClass ||
            kernelInstance instanceof UmlInterface)) {
            System.err.println(String.format("[ClassInterfaceModel]:Wrong " +
                    "Type of Kernel UML Element %s %s",
                kernelInstance.getElementType(), kernelInstance.getName()));
        }
        associationEndsList = new ArrayList<>();
        this.kernelInstance = kernelInstance;
        operationNodeList = new ArrayList<>();
        attributeList = new ArrayList<>();
    }

    public UmlElement getKernelInstance() {
        return kernelInstance;
    }

    /* -------- None Pure Modification Method -------- */
    public void addAssociateEnd(UmlAssociationEnd umlAssociationEnd) {
        associationEndsList.add(umlAssociationEnd);
    }

    public void addOperation(OperationNode operationNode) {
        operationNodeList.add(operationNode);
    }

    public void addAttribute(UmlAttribute umlAttribute) {
        attributeList.add(umlAttribute);
    }

    /* -------- Pure Query Method --------*/
    /* -------- Method with 'Self' mark don't consider generation class*/

    public int getSelfOperationCount(OperationQueryType queryType) {
        int count = 0;
        for (OperationNode operationNode : operationNodeList) {
            switch (queryType) {
                case ALL:
                    count++;
                    break;
                case RETURN:
                    if (operationNode.hasReturn()) {
                        count++;
                    }
                    break;
                case NON_RETURN:
                    if (!operationNode.hasReturn()) {
                        count++;
                    }
                    break;
                case PARAM:
                    if (operationNode.hasIn()) {
                        count++;
                    }
                    break;
                case NON_PARAM:
                    if (!operationNode.hasIn()) {
                        count++;
                    }
                    break;
                default:
                    System.err.println("[OperationCount] Unknown query type.");
            }
        }
        return count;
    }

    public int getSelfAttributesCount() {
        return attributeList.size();
    }

    public int getSelfAssociationAmount() {
        return associationEndsList.size();
    }

    public HashSet<ClassNode> getSelfAssociatedClasses() {
        HashSet<ClassNode> set = new HashSet<>();

        // Get navigator helper instance
        UmlAssociationMap assoMap = UmlAssociationMap.getInstance();
        IdToUmlElement idMap = IdToUmlElement.getInstance();
        NodeNavigator nodeNav = NodeNavigator.getInstance();

        for (UmlAssociationEnd umlAssociationEnd : associationEndsList) {
            UmlAssociation umlAssociation =
                assoMap.getUmlAssoByUmlEnd(umlAssociationEnd);

            // Set Opposite End of Association
            String oppositeEndId;
            if (umlAssociation.getEnd1().equals(umlAssociationEnd.getId())) {
                oppositeEndId = umlAssociation.getEnd2();
            } else {
                oppositeEndId = umlAssociation.getEnd1();
            }

            // Get the element of other side
            UmlAssociationEnd oppositeEnd =
                (UmlAssociationEnd) idMap.getUmlElementById(oppositeEndId);
            String oppositeElementId = oppositeEnd.getReference();

            // Check element's type and record
            if (nodeNav.containsClassNode(oppositeElementId)) {
                set.add(nodeNav.getClassNodeById(oppositeElementId));
            }
        }

        return set;
    }

    public HashMap<Visibility, Integer> getSelfOperationVisibility
        (String methodName) {
        // initial
        HashMap<Visibility, Integer> countMap = new HashMap<>();
        countMap.put(Visibility.PUBLIC, 0);
        countMap.put(Visibility.PROTECTED, 0);
        countMap.put(Visibility.PACKAGE, 0);
        countMap.put(Visibility.PRIVATE, 0);

        // loop for all operation in self class
        for (OperationNode operationNode : operationNodeList) {
            if (operationNode.getKernelInstance().getName().equals(methodName)) {
                Visibility vis = operationNode.getKernelInstance().getVisibility();
                Integer count = countMap.get(vis);
                countMap.replace(vis, count + 1);
            }
        }
        return countMap;
    }

    public Visibility getSelfAttributeVisibility(String name)
        throws AttributeDuplicatedException, AttributeNotFoundException {
        // Attribute duplicated and not-found exception check
        int cnt = 0;
        UmlAttribute save = null;
        for (UmlAttribute umlAttribute : attributeList) {
            if (umlAttribute.getName().equals(name)) {
                cnt++;
                save = umlAttribute;
            }
        }
        if (cnt == 0) {
            throw new AttributeNotFoundException(kernelInstance.getName(), name);
        } else if (cnt > 1) {
            throw new AttributeDuplicatedException(kernelInstance.getName(), name);
        } else {
            return save.getVisibility();
        }
    }

    public List<AttributeClassInformation> getSelfNotHiddenAttribute() {
        ArrayList<AttributeClassInformation> infoList = new ArrayList<>();
        for (UmlAttribute umlAttribute : attributeList) {
            if (umlAttribute.getVisibility() != Visibility.PRIVATE) {
                AttributeClassInformation info =
                    new AttributeClassInformation(umlAttribute.getName(),
                        kernelInstance.getName());
                infoList.add(info);
            }
        }
        return infoList;
    }

    /* ------- Override Hash Method -------- */

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassInterfaceModel) {
            return super.equals(obj);
        }
        return false;
    }
}
