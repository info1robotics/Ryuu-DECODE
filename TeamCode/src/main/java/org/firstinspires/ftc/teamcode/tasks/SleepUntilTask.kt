package org.firstinspires.ftc.teamcode.tasks

/**
 * Waits until a condition is met.
 * The task will continue checking the condition in each tick until it returns true.
 * @param condition A lambda function that returns true when the task should finish.
 */
class SleepUntilTask(private val condition: () -> Boolean) : Task() {

    override fun tick() {
        if (condition()) {
            state = State.FINISHED
        }
    }
}