package org.firstinspires.ftc.teamcode.opmodes.debug

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

@TeleOp
class DrivetrainOnly : LinearOpMode() {
    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y
    override fun runOpMode() {
        Drivetrain.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {

            val forwardPower = gamepad1.corrected_left_stick_y().toDouble()
            val strafePower =   gamepad1.left_stick_x.toDouble()
            val rotationPower = gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()
            Drivetrain.driveMecanum(forwardPower, strafePower, rotationPower, 1.0)
            log.add("powerfl",Drivetrain.fl)
            log.add("powerfr",Drivetrain.fr)
            log.add("powerbl",Drivetrain.bl)
            log.add("powerbr",Drivetrain.br)

            log.tick()
        }
    }
}