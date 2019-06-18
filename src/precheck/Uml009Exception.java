package precheck;

import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import compoent.model.ClassInterfaceModel;

import java.util.Iterator;
import java.util.Set;

import static precheck.Tool.getGenerateOrImplement;

public class Uml009Exception extends UmlGraphException {

    private Uml009Exception() {
        super();
    }

    public static void uml009Check() throws UmlRule009Exception {
        Uml009Exception e = new Uml009Exception();
        Set<UmlClassOrInterface> set = e.check();
        if (!set.isEmpty()) {
            throw new UmlRule009Exception(set);
        }
    }

    @Override
    void dfs(ClassInterfaceModel tar, ClassInterfaceModel node) {
        Iterator<ClassInterfaceModel> nodeIt = getGenerateOrImplement(node);
        while (nodeIt.hasNext()) {
            ClassInterfaceModel nodeTo = nodeIt.next();
            if (isVisited(nodeTo)) {
                addToErrorSet(tar);
                return;
            } else {
                addToVis(nodeTo);
                dfs(tar, nodeTo);
            }
        }
    }
}
