package org.firstinspires.ftc.teamcode.common

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d

object AutoUtil {
    @JvmStatic
    fun v(x: Double, y: Double): Vector2d {
        return Vector2d(x, y)
    }

    @JvmStatic
    fun p(x: Double, y: Double, theta: Double): Pose2d {
        return Pose2d(x, y, theta)
    }

    fun rad(angdeg: Double): Double {
        return Math.toRadians(angdeg)
    }
}
