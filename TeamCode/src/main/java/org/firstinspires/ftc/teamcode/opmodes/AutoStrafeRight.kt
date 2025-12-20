package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@Autonomous()
class AutoStrafeRight : LinearOpMode() {
    override fun runOpMode() {

        val frontLeft = hardwareMap.get(DcMotor::class.java, "leftFront")
        val frontRight = hardwareMap.get(DcMotor::class.java, "rightFront")
        val backLeft = hardwareMap.get(DcMotor::class.java, "leftRear")
        val backRight = hardwareMap.get(DcMotor::class.java, "rightRear")

        frontLeft.direction = DcMotorSimple.Direction.REVERSE
        backLeft.direction = DcMotorSimple.Direction.REVERSE

        // Wait for start
        waitForStart()

        if (opModeIsActive()) {
            // Strafe right power config
            frontLeft.power = 0.5
            backLeft.power = -0.5
            frontRight.power = -0.5
            backRight.power = 0.5

            sleep(3000) // 3 seconds

            // Stop motors
            frontLeft.power = 0.0
            frontRight.power = 0.0
            backLeft.power = 0.0
            backRight.power = 0.0
        }
    }
}