package org.firstinspires.ftc.teamcode.tasks

/**
 * Starts each child in parallel.
 * Ends when all children finish.
 */
class ParallelTask(vararg children: Task) : CompoundTask(*children) {
    override fun tick() {
        var finishedTasks = 0
        for (child in children) {
            if (child.isFinished()) {
                finishedTasks++
                continue
            }
            child.tick()
        }

        if (finishedTasks == children.size) {
            state = State.FINISHED
        }
    }

    override fun run() {
        for (task in children) task.start(context)
    }
}
