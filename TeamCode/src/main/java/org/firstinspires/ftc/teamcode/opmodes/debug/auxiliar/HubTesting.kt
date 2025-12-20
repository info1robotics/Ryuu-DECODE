package org.firstinspires.ftc.teamcode.opmodes.debug.auxiliar

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.opmodes.debug.MotorTesting.Companion.motor

@Config
@TeleOp
class HubTesting: LinearOpMode() {
    companion object {
        @JvmField
        var servoPosition = 0.0

        @JvmField
        var motorPower = 1.0
    }

    override fun runOpMode() {
        /*
        val servo1 = hardwareMap.get(ServoImplEx::class.java, "servo1")
        val servo2 = hardwareMap.get(ServoImplEx::class.java, "servo2")
        val servo3 = hardwareMap.get(ServoImplEx::class.java, "servo3")
        val servo4 = hardwareMap.get(ServoImplEx::class.java, "servo4")
        val servo5 = hardwareMap.get(ServoImplEx::class.java, "servo5")
        val servo6 = hardwareMap.get(ServoImplEx::class.java, "servo6")

         */

        val motor1 = hardwareMap.dcMotor.get("leftFront")
        val motor2 = hardwareMap.dcMotor.get("rightFront")
        val motor3 = hardwareMap.dcMotor.get("leftRear")
        val motor4 = hardwareMap.dcMotor.get("rightRear")
        /*
        servo1.pwmRange = PwmRange(500.0, 2500.0)
        servo2.pwmRange = PwmRange(500.0, 2500.0)
        servo3.pwmRange = PwmRange(500.0, 2500.0)
        servo4.pwmRange = PwmRange(500.0, 2500.0)
        servo5.pwmRange = PwmRange(500.0, 2500.0)

         */

        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            if (servoPosition == -1.0) continue
            /*
            servo1.position = servoPosition
            servo2.position = servoPosition
            servo3.position = servoPosition
            servo4.position = servoPosition
            servo5.position = servoPosition
            servo6.position = servoPosition

             */

            motor1.power = motorPower
            motor2.power = motorPower
            motor3.power = motorPower
            motor4.power = motorPower

        }
    }
}