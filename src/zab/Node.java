package zab;

import org.simgrid.msg.*;
import org.simgrid.msg.Process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Node extends Process {

    protected int id;
    protected String mailbox;

    protected long index = 0;

    public Node(Host host, String name, String[] args) {
        super(host, name, args);
    }

    @Override
    public void main(String[] args) throws MsgException {

        if (args.length != 2) {
            Msg.info("You need to provide 2 arguments.");
            return;
        }
        double initTime = Msg.getClock();

        id = Integer.parseInt(args[0]);
        mailbox = args[0];

        if (id == 0) {
            index++;
            Proposal proposal = new Proposal(index);

        }
    }


    private class Sender extends Process {
        private BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

        @Override
        public void main(String[] strings) throws MsgException {
            while (true) {
                try {
                    Task msg = queue.take();


                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }

        public void add(Task msg) {
            queue.add(msg);
        }
    }
}
