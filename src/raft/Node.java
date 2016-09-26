package raft;

import common.Common;
import org.simgrid.msg.*;
import org.simgrid.msg.Process;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Node extends Process {

    protected int id;
    protected String mailbox;
    protected int numNodes;

    protected long index = 0;
    protected long commit = 0;
    protected long term;
    protected Map<Long, Integer> acks = new HashMap<>();

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
        numNodes = Integer.parseInt(args[1]);
        Comm commReceive = null;
        double clock = Msg.getClock();

        // Leader
        if (id == 0) {
            while (clock < Common.MAX_SIMULATION_TIME) {
                if (commReceive == null) {
                    commReceive = Task.irecv(mailbox);
                }
                if (ThreadLocalRandom.current().nextDouble() <= Common.ALPHA) {
                    Msg.info("Leader: Append operation index=" + (index + 1) + " commit=" + commit);
                    append();
                }
                if (commReceive.test()) {
                    handleReceive(commReceive.getTask());
                }
                clock = Msg.getClock();
                commReceive = null;
            }


        }

        // Follower
        else {
            while (true) {
                if (commReceive == null) {
                    commReceive = Task.irecv(mailbox);
                }
                if (commReceive.test()) {
                    handleReceive(commReceive.getTask());
                }
                commReceive = null;
            }
        }

    }

    private void handleReceive(Task msg) {
        if (msg instanceof Ack) {
            Ack ack = (Ack) msg;
            Msg.info("Leader received " + ack);
            long index = ack.getIndex();
            if (index <= commit) return;
            int acked = acks.get(index);
            if ((index == commit + 1) && (acked + 1 > numNodes / 2)) {
                commit++;
                acks.remove(index);
            } else {
                acks.put(index, acked + 1);
            }
        }

        else if (msg instanceof Operation) {
            Operation op = (Operation) msg;
            Msg.info("Follower received " + op);
            if (op.getIndex() == index + 1) {
                index++;
                Ack ack = new Ack(index);
                ack.isend(String.valueOf(0));
            }
            if (op.getCommit() == commit + 1) {
                commit++;
            }
        }
    }

    private void append() {
        index++;
        acks.put(index, 0);
        for (int i = 1; i < numNodes; i++) {
            Operation operation = new Operation(index, commit);
            operation.dsend(String.valueOf(i));
        }
    }

    private void broadcast(Task msg) {
        for (int i=1; i < numNodes; i++) {
            msg.dsend(String.valueOf(i));
        }
    }
}
