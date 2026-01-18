package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.common.PidController
import org.firstinspires.ftc.teamcode.enums.Colours
import org.firstinspires.ftc.teamcode.pinpoint.Pinpoint
import kotlin.math.PI
import kotlin.math.atan2

object Turret {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0//170 turning right
    var LOWER_LIMIT = 0.0//170 turing left
    var FORWARD_POSITION = 0.5//0 degrees

    private lateinit var servoTurret: ServoImplEx//axon mini mk2 gear ratio 24-50

    fun init(hardwareMap: HardwareMap) {
        servoTurret = hardwareMap.get(ServoImplEx::class.java, "servoTurret")
        servoTurret.pwmRange = PwmRange(500.0, 2500.0)

    }

    fun setPosition(position: Double) {
        servoTurret.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoTurret.position
    }

    var targetX = 0.0
    var targetY = 0.0

    fun lockToTarget(
        robotX: Double,
        robotY: Double,
        robotHeading: Double,
        allianceColour:Colours
    ) {

        if(allianceColour == Colours.BLUE)
        {
            targetX = Pinpoint.BLUE_GOAL_X
            targetY = Pinpoint.BLUE_GOAL_Y
        }
        else
        {
            targetX = Pinpoint.RED_GOAL_X
            targetY = Pinpoint.RED_GOAL_Y

        }

        val dx = targetX - robotX
        val dy = targetY - robotY

        val targetAngleField = atan2(dy, dx)

        var turretAngle = targetAngleField - robotHeading

        // Normalize to [-PI, PI]
        while (turretAngle > PI) turretAngle -= 2 * PI
        while (turretAngle < -PI) turretAngle += 2 * PI

        // Convert to servo position
        val servoPosition =
            FORWARD_POSITION +
                    (turretAngle / PI) * 0.85 +
                    offset

        setPosition(servoPosition)
    }


}