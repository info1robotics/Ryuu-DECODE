package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.common.PidController
import org.firstinspires.ftc.teamcode.enums.Colours

object Turret {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0//180 turning right
    var LOWER_LIMIT = 0.0//180 turing left
    var FORWARD_POSITION = 0.5
    private lateinit var servoTurret: ServoImplEx//axon mini mk2 gear ratio 24-50
    val turretPID =PidController(
        kP = 0.005,
        kI = 0.0,
        kD = 0.0005
    )

    fun init(hardwareMap: HardwareMap) {
        servoTurret = hardwareMap.get(ServoImplEx::class.java, "servoTurret")
        servoTurret.pwmRange = PwmRange(500.0, 2500.0)
        servoTurret.position = 0.5
    }

    fun setPosition(position: Double) {
        servoTurret.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoTurret.position
    }

    fun lock(tx: Double?) {
        if (tx == null) return

        // Deadzone to prevent jitter
        if (kotlin.math.abs(tx) < 0.3) return

        // PID output = position delta
        val delta = turretPID.updateTurret(tx)
            .coerceIn(-0.02, 0.02) // SPEED LIMIT (IMPORTANT)

        val newPos = (servoTurret.position + delta)
            .coerceIn(LOWER_LIMIT, HIGHER_LIMIT)

        servoTurret.position = newPos
    }

}