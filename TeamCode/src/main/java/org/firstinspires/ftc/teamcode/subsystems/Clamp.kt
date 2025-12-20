package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Clamp {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0
    var LOWER_LIMIT = 0.0
    var FAR_POSITION = 0.0
    var CLOSE_POSITION = 0.0
    private lateinit var servoClamp: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoClamp = hardwareMap.get(ServoImplEx::class.java, "servoClamp")
        servoClamp.pwmRange = PwmRange(500.0, 2500.0)
        servoClamp.position = FAR_POSITION
    }

    fun setPosition(position: Double) {
        servoClamp.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoClamp.position
    }

}