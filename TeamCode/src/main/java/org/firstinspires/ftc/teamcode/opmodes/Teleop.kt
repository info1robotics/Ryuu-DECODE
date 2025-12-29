package org.firstinspires.ftc.teamcode.opmodes

import android.view.ViewStructure.HtmlInfo
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Hood
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Jack
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
import kotlin.time.times

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
    var empty = 1.0
    private fun handleInputIntake()
    {
        if(!Intake.isEmpty())
        {
            empty=0.0
        }

        Joint.setPosition(Joint.COLLECT_POSITION)
        if(!transition)
        {
            Intake.setPowerSupport((gamepad2.right_trigger.toDouble())*(empty*(0.7)))
            Intake.setPowerMain(gamepad2.right_trigger.toDouble())

            if (gamepad2.left_trigger > 0.1 ) {
                Intake.reverse()
                empty = 1.0
            }

        }
    }
    private fun handleInputJack()
    {
        if(gamepad2.dpad_up)
            Jack.setPosition(Jack.LOWER_LIMIT)
        if(gamepad2.dpad_down)
            Jack.setPosition(Jack.HIGHER_LIMIT)
    }
    var INDEX = 0

    var transition = false
    private fun handleInputShooter() {
        if(gamepadEx2.getButtonDown("a"))
        {
            transition=true
            Shooter.setRPM(4500.0)
            actionQueue.add(1600)
            {
                Intake.setPowerSupport(0.5)
                actionQueue.add(100)
                {
                        Intake.stop()
                        actionQueue.add(600)
                        {
                            Intake.setPowerSupport(0.8)
                            Intake.setPowerMain(0.7)
                            actionQueue.add(100)
                            {
                                Intake.stop()
                                actionQueue.add(600)
                                {
                                    Intake.take()
                                    actionQueue.add(900)
                                    {
                                        Intake.stop()
                                        Shooter.stop()
                                        transition=false
                                        empty=1.0
                                    }
                                }
                            }



                    }

                }
            }

        }
    }
    fun handleInputTurret() {

    }

    override fun runOpMode() {
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)
        Controller.init(hardwareMap)
        /*
        Limelight.start()
        Limelight.changePipeline(0)

         */
        val log = Log(telemetry)


        Controller.setInit()
        waitForStart()

        while (opModeIsActive() && !isStopRequested) {

            handleInputDrivetrain()
            handleInputIntake()
            handleInputShooter()
            handleInputTurret()
            handleInputJack()
            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()
            log.add("is empty",Intake.isEmpty())
            log.add("rgb",Intake.getColorReading())
            log.tick()
        }
    }
}