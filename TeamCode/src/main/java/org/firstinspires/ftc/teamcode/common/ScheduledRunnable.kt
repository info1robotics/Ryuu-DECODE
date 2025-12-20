package org.firstinspires.ftc.teamcode.common

import java.util.function.Supplier

class ScheduledRunnable {
    private val runnable: Runnable
    val delay: Long
    @kotlin.jvm.JvmField
    val type: String
    val startTime: Long
    var condition: Supplier<Boolean>? = null

    constructor(runnable: Runnable, delay: Long, type: String, condition: Supplier<Boolean>) {
        this.runnable = runnable
        this.delay = delay
        this.type = type
        this.startTime = System.currentTimeMillis()
        this.condition = condition
    }

    constructor(runnable: Runnable, delay: Long, type: String) {
        this.runnable = runnable
        this.delay = delay
        this.type = type
        this.startTime = System.currentTimeMillis()
    }

    constructor(runnable: Runnable, delay: Long) {
        this.runnable = runnable
        this.delay = delay
        this.type = ""
        this.startTime = System.currentTimeMillis()
    }

    constructor(runnable: Runnable, delay: Long, condition: Supplier<Boolean>) {
        this.runnable = runnable
        this.delay = delay
        this.type = ""
        this.startTime = System.currentTimeMillis()
        this.condition = condition
    }

    fun run() {
        runnable.run()
    }

    val isDue: Boolean
        get() = (System.currentTimeMillis() - startTime >= delay) || (condition?.get() == true)
}
