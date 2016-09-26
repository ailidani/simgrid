package zab;

import org.simgrid.msg.Task;

public class Ack extends Task {

    private long index;

    public Ack(long index) {
        this.index = index;
    }

    public long getIndex() {
        return index;
    }
}
