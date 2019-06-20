package handler;

import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import navigate.NodeNavigator;

import java.util.Iterator;
import java.util.List;

public class AddElementHandler {
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static void addHandler(List<UmlElement> elementList) {

        // Step 1: Process Top Element : Class, Interface,
        // State Machine & Region, Interaction
        handleTopElement(elementList);

        // Step 2: Process Middle Element : Operation, State, Lifeline
        handleMiddleElement(elementList);

        // Step 3: General Handle
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            GeneralHandler.handleElement(it.next());
        }
    }

    /**
     * To handler Top uml elements to prepare other elements' container.
     * 1. Class Node
     * 2. Interface Node
     * 3. State Machine Node
     * 4. Region Node (must follow parent state machine) // todo
     * 5. Interaction Node
     *
     * @param elementList elements remains to be proceeded.
     */
    private static void handleTopElement(List<UmlElement> elementList) {
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlClass) {
                it.remove();
                GeneralHandler.handleElement(element);
            } else if (element instanceof UmlInterface) {
                it.remove();
                GeneralHandler.handleElement(element);
            } else if (element instanceof UmlStateMachine) {
                it.remove();
                GeneralHandler.handleElement(element);
            } else if (element instanceof UmlInteraction) {
                it.remove();
                GeneralHandler.handleElement(element);
            }
        }

        it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlRegion) {
                it.remove();
                GeneralHandler.handleElement(element);
            }
        }
    }

    /**
     * Middle elements are bridge between top and bottom.
     * 1. Operation Node
     * 2. State Node
     * 3. LifeLine Node
     *
     * @param elementList Elements remains to be proceeded.
     */
    private static void handleMiddleElement(List<UmlElement> elementList) {
        Iterator<UmlElement> it = elementList.iterator();
        while (it.hasNext()) {
            UmlElement element = it.next();
            if (element instanceof UmlOperation) {
                it.remove();
                GeneralHandler.handleElement(element);
            } else if (element instanceof UmlState
                || element instanceof UmlFinalState
                || element instanceof UmlPseudostate) {
                it.remove();
                GeneralHandler.handleElement(element);
            } else if (element instanceof UmlLifeline) {
                it.remove();
                GeneralHandler.handleElement(element);
            }
        }
    }

}
