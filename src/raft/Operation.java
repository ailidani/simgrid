package raft;

import org.simgrid.msg.Task;

import static common.Common.TASK_SIZE;
import static common.Common.TASK_TIME;

public class Operation extends Task {

    private long index;
    private long commit;

    public Operation(long index, long commit) {
        super(String.valueOf(index), TASK_TIME, TASK_SIZE);
        this.index = index;
        this.commit = commit;
    }

    public long getIndex() {
        return index;
    }

    public long getCommit() {
        return commit;
    }
}
