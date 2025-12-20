package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "Basic Tank Drive")
class BasicTankDrive : LinearOpMode() {
    override fun runOpMode() {
        val motorFL = hardwareMap.get(DcMotor::class.java, "motorFL")
        val motorFR = hardwareMap.get(DcMotor::class.java, "motorFR")
        val motorBL = hardwareMap.get(DcMotor::class.java, "motorBL")
        val motorBR = hardwareMap.get(DcMotor::class.java, "motorBR")

        motorFL.direction = DcMotorSimple.Direction.FORWARD
        motorFR.direction = DcMotorSimple.Direction.REVERSE
        motorBL.direction = DcMotorSimple.Direction.FORWARD
        motorBR.direction = DcMotorSimple.Direction.FORWARD

        motorFL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        motorFR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        motorBL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        motorBR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        telemetry.addLine("Ready to run")
        telemetry.update()
        waitForStart()

        while (opModeIsActive()) {
            val drive = -gamepad1.left_stick_y  // Forward/backward
            val turn = gamepad1.left_trigger-gamepad1.right_trigger  // Turning

            val leftPower = (drive + turn).coerceIn((-1.0).toFloat(), 1.0F)
            val rightPower = (drive - turn).coerceIn((-1.0).toFloat(), 1.0F)

            motorFL.power = leftPower.toDouble()
            motorBL.power = leftPower.toDouble()
            motorFR.power = rightPower.toDouble()
            motorBR.power = rightPower.toDouble()

            telemetry.addData("Left Power", leftPower)
            telemetry.addData("Right Power", rightPower)
            telemetry.update()
        }
    }
}