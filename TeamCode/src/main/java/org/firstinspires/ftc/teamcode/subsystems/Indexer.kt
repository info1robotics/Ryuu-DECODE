package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Indexer {

    var offset = 0.0
    var HIGHER_LIMIT = 1.0
    var LOWER_LIMIT = 0.0
    var FIRST_POSITION = 0.2
    var SECOND_POSITION = 0.59
    var THIRD_POSITION = 1.0
    private lateinit var servoIndexer: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoIndexer = hardwareMap.get(ServoImplEx::class.java, "servoIndexer")
        servoIndexer.pwmRange = PwmRange(500.0, 2500.0)
        servoIndexer.position = FIRST_POSITION
    }

    fun setPosition(position: Double) {
        servoIndexer.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoIndexer.position
    }

}