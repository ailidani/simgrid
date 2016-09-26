package zab;

import org.simgrid.msg.Task;

import static common.Common.TASK_SIZE;
import static common.Common.TASK_TIME;

public class Proposal extends Task {

    private long index;

    public Proposal(long index) {
        super(String.valueOf(index), TASK_TIME, TASK_SIZE);
        this.index = index;
    }

    public long getIndex() { return index; }
}
