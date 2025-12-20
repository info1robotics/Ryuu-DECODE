package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Jack {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0
    var LOWER_LIMIT = 0.0
    private lateinit var servoJack: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoJack = hardwareMap.get(ServoImplEx::class.java, "servoJack")
        servoJack.pwmRange = PwmRange(500.0, 2500.0)
        servoJack.position = 0.0
    }

    fun setPosition(position: Double) {
        servoJack.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoJack.position
    }

}