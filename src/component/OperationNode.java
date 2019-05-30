package component;

import com.oocourse.uml1.models.common.Direction;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

import java.util.ArrayList;

public class OperationNode {
    private ArrayList<UmlParameter> parameterList;
    private UmlOperation kernelInstance;
    private Boolean hasReturn;
    private Boolean hasIn;

    public OperationNode(UmlOperation umlOperation) {
        parameterList = new ArrayList<>();
        kernelInstance = umlOperation;
        hasIn = null;
        hasReturn = null;
    }

    public UmlOperation getKernelInstance() {
        return kernelInstance;
    }

    /* ------- Modification Type Method -------- */
    public void addParameter(UmlParameter umlParameter) {
        parameterList.add(umlParameter);
    }

    /* -------- OperationNode Type Query -------- */
    public boolean hasReturn() {
        if (hasReturn != null) {
            return hasReturn;
        }

        for (UmlParameter umlParameter : parameterList) {
            if (umlParameter.getDirection() == Direction.RETURN) {
                hasReturn = Boolean.TRUE;
                return hasReturn;
            }
        }
        hasReturn = Boolean.FALSE;
        return hasReturn;
    }

    // Check if exist parameter other than RETURN in this kernelInstance.
    // NOTES: IN INOUT OUT are all regraded as import parameter.
    public boolean hasIn() {
        if (hasIn != null) {
            return hasIn;
        }

        for (UmlParameter umlParameter : parameterList) {
            if (umlParameter.getDirection() != Direction.RETURN) {
                hasIn = Boolean.TRUE;
                return hasIn;
            }
        }
        hasIn = Boolean.FALSE;
        return hasIn;
    }
}