package raft;

import org.simgrid.msg.Task;

public class Ack extends Task {

    private long index;

    public Ack(long index) {
        super(String.valueOf(index), 0, Long.BYTES);
        this.index = index;
    }

    public long getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Ack {index=" + index + '}';
    }
}
