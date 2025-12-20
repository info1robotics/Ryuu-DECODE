package org.firstinspires.ftc.teamcode.opmodes

import android.app.Notification.Action
import android.service.controls.Control
import com.acmerobotics.roadrunner.clamp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp
class Teleop : LinearOpMode() {

    //TODO add turret to ds
    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y

    lateinit var gamepadEx1: GamepadEx
    lateinit var gamepadEx2: GamepadEx
    val actionQueue = ActionQueue()

    private fun handleInputDrivetrain()
    {
        val forwardPower = gamepad1.corrected_left_stick_y().toDouble()
        val strafePower =   gamepad1.left_stick_x.toDouble()
        val primaryRotationPower = gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()

        Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
    }
    private fun handleInputIntake()
    {
        if(!transition)
        {
            if (gamepad2.right_trigger > 0.1) {
                Intake.take()
            } else if (gamepad2.left_trigger> 0.1 ){
                Intake.reverse()
            }
            else
                Intake.setPower(0.0)
        }
    }
    var INDEX = 0

    var transition = false
    private fun handleInputShooter()
    {

    }
    val STICK_CONVERTER = 125
    fun handleInputTurret() {

    }

    override fun runOpMode() {
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)
        Controller.init(hardwareMap)
        Limelight.start()
        Limelight.changePipeline(0)
        Turret.resetEncoder()
        val log = Log(telemetry)


        Controller.setInit()
        waitForStart()

        while (opModeIsActive() && !isStopRequested) {

            handleInputDrivetrain()
            handleInputIntake()
            handleInputShooter()
            handleInputTurret()
            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()
            log.add(Turret.getTargetPosition().toString())
            log.tick()
        }
    }
}