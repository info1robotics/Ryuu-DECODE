package org.firstinspires.ftc.teamcode.opmodes.debug.auxiliar

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx

//@Disabled
@Config
@TeleOp
//@Photon
class DualServoTesting: LinearOpMode() {
    companion object {
        @JvmField
        var servoPosition = 1.0
    }

    override fun runOpMode() {
        val servo1 = hardwareMap.get(ServoImplEx::class.java, "servo1")
        val servo2 = hardwareMap.get(ServoImplEx::class.java, "servo2")
        servo2.direction = Servo.Direction.REVERSE

        servo1.pwmRange = PwmRange(500.0, 2500.0)
        servo2.pwmRange = PwmRange(500.0, 2500.0)

        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            if (servoPosition == -1.0) continue; // skip starting position
            servo1.position = servoPosition
            servo2.position = servoPosition
        }
    }
}