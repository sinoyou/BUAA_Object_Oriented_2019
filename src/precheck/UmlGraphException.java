package precheck;

import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import compoent.model.ClassInterfaceModel;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static precheck.Tool.getClassOrInterface;

public abstract class UmlGraphException {
    // define set to store class and interfaces on the extends cycle
    private HashSet<ClassInterfaceModel> errorSet;

    // dfs help attributes
    private HashSet<ClassInterfaceModel> vis;

    public UmlGraphException() {
        errorSet = new HashSet<>();
        vis = new HashSet<>();
    }

    public Set<UmlClassOrInterface> check() {
        // loop over for all classes and interfaces
        Iterator<ClassInterfaceModel> it = getClassOrInterface();
        while (it.hasNext()) {
            // initial data structure
            ClassInterfaceModel node = it.next();
            initialVis();
            addToVis(node);
            // start dfs
            dfs(node, node);
        }

        HashSet<UmlClassOrInterface> set = new HashSet<>();
        for (ClassInterfaceModel node : errorSet) {
            UmlElement element = node.getKernelInstance();
            if (element instanceof UmlClass) {
                set.add((UmlClass) element);
            } else if (element instanceof UmlInterface) {
                set.add((UmlInterface) element);
            } else {
                System.err.println(String.format("[uml008/9]:Wrong type when adding %s", element.getId()));
            }
        }
        return set;
    }

    /* -------- Inner Help Function -------- */
    public void addToErrorSet(ClassInterfaceModel node) {
        errorSet.add(node);
    }

    public void initialVis() {
        vis.clear();
    }

    public void addToVis(ClassInterfaceModel node) {
        vis.add(node);
    }

    public boolean isVisited(ClassInterfaceModel node) {
        return vis.contains(node);
    }

    abstract void dfs(ClassInterfaceModel tar, ClassInterfaceModel node);
}
