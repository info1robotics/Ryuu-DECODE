package org.firstinspires.ftc.teamcode.tasks

import java.util.function.Supplier


class ConditionalTask(var condition: Supplier<Boolean>, var task: Task) : Task() {
    override fun tick() {
        if (task.isFinished()) {
            state = State.FINISHED
            return
        }
        task.tick()
    }

    override fun run() {
        if (!condition.get()) {
            state = State.FINISHED
            return
        }
        task.start(this.context)
    }
}
