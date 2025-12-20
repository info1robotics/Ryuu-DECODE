package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Glider {
    //TODO reverse 53 and 33

    var offset = 0.0
    var HIGHER_LIMIT = 0.68 //~33 degrees
    var LOWER_LIMIT = 0.0   //~53 degrees

    var FAR_DEGREE = 40.0
    private val distanceAngleTable = listOf(
        0.5 to 53.0,
        1.0 to 53.0,
        1.5 to 53.0,
        2.0 to 53.0,
        2.5 to 53.0,
        3.0 to 33.0

    )

    private lateinit var servoGlider: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoGlider = hardwareMap.get(ServoImplEx::class.java, "servoGlider")
        servoGlider.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoGlider.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun setPositionDeg(degrees: Double) {
        val minDeg = 33.0   // corresponds to HIGHER_LIMIT
        val maxDeg = 53.0   // corresponds to LOWER_LIMIT

        val clampedDeg = degrees.coerceIn(minDeg, maxDeg)

        // Map degrees to servo position: 33° -> HIGHER_LIMIT, 53° -> LOWER_LIMIT
        val position = HIGHER_LIMIT - (clampedDeg - minDeg) * (HIGHER_LIMIT - LOWER_LIMIT) / (maxDeg - minDeg)

        servoGlider.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoGlider.position
    }

    fun autoAim(distance: Double) {
        val clampedDistance = distance.coerceIn(
            distanceAngleTable.first().first,
            distanceAngleTable.last().first
        )

        val lower = distanceAngleTable.lastOrNull { it.first <= clampedDistance } ?: distanceAngleTable.first()
        val upper = distanceAngleTable.firstOrNull { it.first >= clampedDistance } ?: distanceAngleTable.last()

        val angle = if (lower == upper) {
            lower.second
        } else {
            val (d1, a1) = lower
            val (d2, a2) = upper
            a1 + (clampedDistance - d1) * (a2 - a1) / (d2 - d1)
        }

        val adjustedAngle = angle + offset

        setPositionDeg(adjustedAngle)
    }
}