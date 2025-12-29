package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx

object Jack {

    var offset = 0.02
    var INIT_POSITION = 0.91
    var HIGHER_LIMIT = 0.91
    var LOWER_LIMIT = 0.55
    var STEP = 0.05
    private lateinit var servoJackFirst: ServoImplEx
    private lateinit var servoJackSecond: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoJackFirst = hardwareMap.get(ServoImplEx::class.java, "servoJackLeft")
        servoJackSecond = hardwareMap.get(ServoImplEx::class.java, "servoJackRight")
        servoJackSecond.direction = Servo.Direction.REVERSE
        servoJackFirst.pwmRange = PwmRange(500.0, 2500.0)
        servoJackSecond.pwmRange = PwmRange(500.0, 2500.0)
        servoJackFirst.position = INIT_POSITION
        servoJackSecond.position = INIT_POSITION
    }

    fun setPosition(position: Double) {
        servoJackFirst.position = position.coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
        servoJackSecond.position = (position + offset).coerceIn(LOWER_LIMIT, HIGHER_LIMIT)
    }

    fun getPosition(): Double {
        return servoJackFirst.position
    }
    fun getOffsetedPosition():Double
    {
        return  servoJackSecond.position
    }


}