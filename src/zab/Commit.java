package zab;

import org.simgrid.msg.Task;

public class Commit extends Task {

    private long index;

    public Commit(long index) {
        this.index = index;
    }

    public long getIndex() {
        return index;
    }
}
