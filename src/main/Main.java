package main;

import com.oocourse.uml2.interact.AppRunner;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            MyUmlGeneralInteraction.setMode(args[0]);
        }
        AppRunner appRunner =
            AppRunner.newInstance(MyUmlGeneralInteraction.class);
        appRunner.run(args);
    }
}