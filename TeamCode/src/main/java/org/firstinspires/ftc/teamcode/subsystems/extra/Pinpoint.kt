package org.firstinspires.ftc.teamcode.pinpoint

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.enums.Colours
import kotlin.math.pow
import kotlin.math.sqrt

object Pinpoint {
    const val RED_GOAL_X = -58.37
    const val RED_GOAL_Y = 55.64
    const val BLUE_GOAL_X = -58.37
    const val BLUE_GOAL_Y = -55.64

    fun init(hardwareMap: HardwareMap) {}

    fun distance(currentX: Double, currentY: Double, colour: Colours): Double {
        val (goalX, goalY) = when (colour) {
            Colours.RED -> RED_GOAL_X to RED_GOAL_Y
            Colours.BLUE -> BLUE_GOAL_X to BLUE_GOAL_Y

        }

        return sqrt(
            (goalX - currentX).pow(2) +
                    (goalY - currentY).pow(2)
        )
    }
}