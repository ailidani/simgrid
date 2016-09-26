package raft;

import org.simgrid.msg.Msg;

public class Main {

    private Main() {
        throw new IllegalAccessError("Utility class");
    }

    public static void main(String[] args) {
        Msg.init(args);

        if(args.length < 2) {
            Msg.info("Usage   : raft platform_file deployment_file");
            Msg.info("example : raft ../common/platform.xml raft.xml");
            System.exit(1);
        }

        /* construct the platform and deploy the application */
        Msg.createEnvironment(args[0]);
        Msg.deployApplication(args[1]);

        /*  execute the simulation. */
        Msg.run();
    }
}
