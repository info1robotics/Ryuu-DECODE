package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Turret {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0
    var LOWER_LIMIT = 0.0
    private lateinit var servoTurretFirst: ServoImplEx
    private lateinit var servoTurretSecond: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoTurretFirst = hardwareMap.get(ServoImplEx::class.java, "servoTurretFirst")
        servoTurretSecond = hardwareMap.get(ServoImplEx::class.java, "servoTurretSecond")
        servoTurretFirst.pwmRange = PwmRange(500.0, 2500.0)
        servoTurretSecond.pwmRange = PwmRange(500.0, 2500.0)
        servoTurretFirst.position = 0.0
        servoTurretSecond.position = 0.0
    }

    fun setPosition(position: Double) {
        servoTurretFirst.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
        servoTurretSecond.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoTurretFirst.position
    }
    fun lock()
    {

    }

}