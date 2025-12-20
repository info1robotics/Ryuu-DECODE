package org.firstinspires.ftc.teamcode.opmodes.debug.auxiliar

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

//@Disabled
@Config
@TeleOp
//@Photon
class ServoTesting: LinearOpMode() {
    companion object {
        @JvmField
        var servoPosition = 0.0
    }

    override fun runOpMode() {
        val servo = hardwareMap.get(ServoImplEx::class.java, "servo")
        servo.pwmRange = PwmRange(500.0, 2500.0)

        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            //if (servoPosition == -1.0) continue; // skip starting position
            servo.position = servoPosition
        }
    }
}