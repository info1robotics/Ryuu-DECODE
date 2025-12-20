package org.firstinspires.ftc.teamcode.tasks

import org.firstinspires.ftc.teamcode.opmodes.AutoBase

abstract class Task {
    fun isFinished() = state == State.FINISHED

    fun isRunning() = state == State.RUNNING

    enum class State {
        DEFAULT,
        RUNNING,
        FINISHED,
    }

    var state: State = State.DEFAULT
    var context: AutoBase? = null

    open fun run() {}
    open fun tick() {}

    fun start(context: AutoBase?) {
        this.context = context
        state = State.RUNNING
        run()
    }
}