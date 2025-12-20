package org.firstinspires.ftc.teamcode.tasks

import java.util.Arrays
import java.util.stream.Stream

open class CompoundTask(vararg tasks: Task) : Task() {
    var children: MutableList<Task> = mutableListOf(*tasks)

    fun add(task: Task) {
        children.add(task)
    }
}
