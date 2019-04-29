import com.oocourse.specs1.AppRunner;
import homework.MyPath;
import homework.MyPathContainer;

public class Main {
    public static void main(String[] args) throws Exception {
        AppRunner runner = AppRunner.newInstance(MyPath.class,
            MyPathContainer.class);
        runner.run(args);
    }
}