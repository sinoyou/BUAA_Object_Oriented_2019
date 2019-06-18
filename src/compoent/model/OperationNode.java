package compoent.model;

import com.oocourse.uml2.models.common.Direction;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;

import java.util.ArrayList;

public class OperationNode {
    private ArrayList<UmlParameter> parameterList;
    private UmlOperation kernelInstance;
    private Boolean hasReturn;
    private Boolean hasIn;

    // cache version control
    private int version;
    private int hasInVersion;
    private int hasReturnVersion;

    public OperationNode(UmlOperation umlOperation) {
        parameterList = new ArrayList<>();
        kernelInstance = umlOperation;
        hasIn = null;
        hasReturn = null;

        version = 0;
        hasInVersion = 0;
        hasReturnVersion = 0;
    }

    public UmlOperation getKernelInstance() {
        return kernelInstance;
    }

    /* ------- Modification Type Method -------- */
    public void addParameter(UmlParameter umlParameter) {
        parameterList.add(umlParameter);
        version++;
    }

    /* -------- OperationNode Type Query -------- */
    public boolean hasReturn() {
        if (version == hasReturnVersion && hasReturn != null) {
            return hasReturn;
        }
        for (UmlParameter umlParameter : parameterList) {
            if (umlParameter.getDirection() == Direction.RETURN) {
                hasReturnVersion = version;
                hasReturn = Boolean.TRUE;
                return hasReturn;
            }
        }
        hasReturnVersion = version;
        hasReturn = Boolean.FALSE;
        return hasReturn;
    }

    // Check if exist parameter other than RETURN in this kernelInstance.
    // NOTES: IN INOUT OUT are all regraded as import parameter.
    public boolean hasIn() {
        if (version == hasInVersion && hasIn != null) {
            return hasIn;
        }

        for (UmlParameter umlParameter : parameterList) {
            if (umlParameter.getDirection() != Direction.RETURN) {
                hasInVersion = version;
                hasIn = Boolean.TRUE;
                return hasIn;
            }
        }
        hasInVersion = version;
        hasIn = Boolean.FALSE;
        return hasIn;
    }
}