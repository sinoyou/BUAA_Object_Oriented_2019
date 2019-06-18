package precheck;

import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import compoent.model.ClassInterfaceModel;

import java.util.Iterator;
import java.util.Set;

import static precheck.Tool.getGenerateOrImplement;

public class Uml008Exception extends UmlGraphException {

    public static void uml008Check() throws UmlRule008Exception {
        Uml008Exception e = new Uml008Exception();
        Set<UmlClassOrInterface> set = e.check();
        if (!set.isEmpty()) {
            throw new UmlRule008Exception(set);
        }
    }

    private Uml008Exception() {
        super();
    }

    @Override
    public void dfs(ClassInterfaceModel tar, ClassInterfaceModel node) {
        Iterator<ClassInterfaceModel> nodeIt = getGenerateOrImplement(node);
        while (nodeIt.hasNext()) {
            ClassInterfaceModel nodeTo = nodeIt.next();
            if (nodeTo.equals(tar)) {
                addToErrorSet(tar);
                return;
            } else if (!isVisited(nodeTo)) {
                addToVis(nodeTo);
                dfs(tar, nodeTo);
            }
        }
    }
}
