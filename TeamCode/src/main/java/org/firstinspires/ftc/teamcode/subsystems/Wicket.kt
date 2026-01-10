package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx

object Wicket {

    var OPEN_POSITION = 0.4
    var CLOSE_POSITION = 0.7

    private lateinit var servoJoint: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoJoint = hardwareMap.get(ServoImplEx::class.java, "servoWicket")
        servoJoint.direction = Servo.Direction.REVERSE
        servoJoint.pwmRange = PwmRange(500.0, 2500.0)
        servoJoint.position = CLOSE_POSITION
    }

    fun setPosition(position: Double) {
        servoJoint.position = position
    }

    fun getPosition(): Double {
        return servoJoint.position
    }


}