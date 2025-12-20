package org.firstinspires.ftc.teamcode.common

class ActionQueue : ArrayList<ScheduledRunnable?>() {
    fun update() {
        var i = 0
        while (i < size) {
            val action = get(i)
            if (action!!.isDue) {
                action.run()
                removeAt(i)
                i--
            }
            i++
        }
    }

    fun clear(type: String?) {
        var i = 0
        while (i < size) {
            if (get(i)?.type.equals(type)) {
                removeAt(i)
                i--
            }
            i++
        }
    }

    fun add(unit: () -> Unit, delay: Long, type: String) {
        add(ScheduledRunnable(unit, delay, type))
    }

    fun add(unit: () -> Unit, delay: Long) {
        add(ScheduledRunnable(unit, delay))
    }

    fun add(delay: Long, unit: () -> Unit) {
        add(ScheduledRunnable(unit, delay))
    }

    fun add(delay: Long, type: String, unit: () -> Unit) {
        add(ScheduledRunnable(unit, delay, type))
    }

    fun add(delay: Long, type: String, condition: () -> Boolean, unit: () -> Unit) {
        add(ScheduledRunnable(unit, delay, type, condition))
    }

    fun add(delay: Long, condition: () -> Boolean, unit: () -> Unit) {
        add(ScheduledRunnable(unit, delay, condition))
    }
}

