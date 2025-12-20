package org.firstinspires.ftc.teamcode.tasks

import com.acmerobotics.roadrunner.Action
import org.firstinspires.ftc.teamcode.common.Log
import java.util.function.Supplier


object TaskBuilder {
    fun serial(vararg task: Task): SerialTask {
        return SerialTask(*task)
    }

    fun parallel(vararg task: Task): ParallelTask {
        return ParallelTask(*task)
    }

    fun conditional(condition: Supplier<Boolean>, task: Task): ConditionalTask {
        return ConditionalTask(condition, task)
    }

    fun sleepms(ms: Long): SleepTask {
        return SleepTask(ms)
    }

    fun execute(runnable: Runnable): ExecuteTask {
        return ExecuteTask(runnable)
    }

    fun log(message: String): ExecuteTask {
        return execute {
            Log.instance.add(message)
        }
    }

    fun log(name: String?, line: Any): ExecuteTask {
        return execute {
            Log.instance.add(name, line)
        }
    }

    fun action(action: Action): ActionTask {
        return ActionTask(action)
    }

    fun sleepuntil(condition: () -> Boolean): SleepUntilTask {
        return SleepUntilTask(condition)
    }
}
