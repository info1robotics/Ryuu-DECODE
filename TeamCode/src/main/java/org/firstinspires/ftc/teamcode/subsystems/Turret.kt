package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Turret {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0//180 turning right
    var LOWER_LIMIT = 0.0//180 turing left
    var FORWARD_POSITION = 0.5
    private lateinit var servoTurret: ServoImplEx//five turn servo

    fun init(hardwareMap: HardwareMap) {
        servoTurret = hardwareMap.get(ServoImplEx::class.java, "servoTurret")
        servoTurret.pwmRange = PwmRange(500.0, 2500.0)
        servoTurret.position = FORWARD_POSITION
    }

    fun setPosition(position: Double) {
        servoTurret.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoTurret.position
    }
    fun lock(heading:Double)
    {
        servoTurret.position = heading
    }

}