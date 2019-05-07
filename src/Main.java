import com.oocourse.specs2.AppRunner;
import hm10.graph.MyGraph;
import hm10.graph.component.MyPath;

public class Main {
    public static void main(String[] args) throws Exception {
        AppRunner runner = AppRunner.newInstance(MyPath.class,
            MyGraph.class);
        runner.run(args);
    }
}