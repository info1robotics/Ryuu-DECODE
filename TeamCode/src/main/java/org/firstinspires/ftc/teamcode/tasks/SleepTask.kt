package org.firstinspires.ftc.teamcode.tasks

/**
 * Waits for the given amount of time.
 * Unless used in a synchronous task, this will not block the thread of execution.
 * @param ms Time to wait in milliseconds.
 */
class SleepTask(var ms: Long) : Task() {
    var timeAtRun: Long = -1

    override fun tick() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - timeAtRun > ms) state = State.FINISHED
    }

    override fun run() {
        timeAtRun = System.currentTimeMillis()
    }
}
