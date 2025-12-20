package org.firstinspires.ftc.teamcode.tasks

/**
 * Runs the given block synchronously.
 * Intended for one-liners.
 */
class ExecuteTask(private val runnable: Runnable) : Task() {
    override fun run() {
        runnable.run()
        state = State.FINISHED
    }
}
