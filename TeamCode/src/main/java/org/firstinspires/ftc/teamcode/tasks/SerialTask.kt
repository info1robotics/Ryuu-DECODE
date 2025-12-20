package org.firstinspires.ftc.teamcode.tasks

class SerialTask(vararg children: Task) : CompoundTask(*children) {
    var currentTask: Int = 0

    override fun tick() {
        if (children[currentTask].isFinished()) {
            currentTask++
            if (currentTask >= children.size) {
                state = State.FINISHED
                currentTask = 0
                for (child in children) child.state = State.DEFAULT
                return
            }
            children[currentTask].start(this.context)
        } else if (children[currentTask].isRunning()) {
            children[currentTask].tick()
        }
    }

    override fun run() {
        if (children.isEmpty()) {
            state = State.FINISHED
            return
        }
        children[0].start(context)
    }
}
