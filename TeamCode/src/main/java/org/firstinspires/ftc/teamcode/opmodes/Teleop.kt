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
import org.firstinspires.ftc.teamcode.subsystems.Clamp
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Indexer
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
    private fun handleInputIndexer()
    {
        if(gamepad1.dpad_left)
            Indexer.setPosition(Indexer.getPosition()-0.1)
        else if(gamepad1.dpad_right)
            Indexer.setPosition(Indexer.getPosition()+0.1)


        else if(gamepadEx2.getButtonDown("bumper_left"))
            Indexer.setPosition(Indexer.FIRST_POSITION)
        else if(gamepadEx2.getButtonDown("bumper_right"))
            Indexer.setPosition(Indexer.THIRD_POSITION)
        else if(gamepadEx2.getButtonDown("touchpad"))
            Indexer.setPosition(Indexer.SECOND_POSITION)


    }
    private fun handleInputClamp()
    {

        if(gamepadEx2.getButtonDown("dpad_up"))
            Clamp.setPosition(Clamp.CLOSE_POSITION)
        else if(gamepadEx2.getButtonDown("dpad_down"))
            Clamp.setPosition(Clamp.FAR_POSITION)

    }
    var transition = false
    private fun handleInputShooter()
    {
        if(gamepadEx2.getButtonDown("b"))
        {
            transition=true
            Shooter.setRPM(4500.0)//4500 for far
            Glider.setPositionDeg(33.0)
            actionQueue.add(1200)
            {
                Intake.take()
                actionQueue.add(300)
                {
                    Shooter.setRPM(0.0)
                    transition=false
                }
            }
        }
        else if(gamepadEx2.getButtonDown("x"))
        {
            transition=true
            Shooter.setRPM(5000.0)
            Glider.setPositionDeg(40.0)
            actionQueue.add(1300)
            {
                Intake.take()
                actionQueue.add(300)
                {
                    Shooter.setRPM(0.0)
                    transition=false
                }
            }
        }
        else if(gamepadEx2.getButtonDown("y"))
        {
            transition=true
            Indexer.setPosition(Indexer.FIRST_POSITION)
            Shooter.setRPM(5000.0)
            Glider.setPositionDeg(40.0)
            actionQueue.add(800)
            {
                Intake.take()
                actionQueue.add(2200)
                {
                    Intake.stop()
                    Indexer.setPosition(Indexer.SECOND_POSITION)
                    actionQueue.add(200)
                    {
                        Intake.take()
                        actionQueue.add(1800)
                        {   Intake.stop()
                            Indexer.setPosition(Indexer.THIRD_POSITION)
                            actionQueue.add(200)
                            {
                                Intake.take()
                                actionQueue.add(2100)
                                {
                                    Indexer.setPosition(Indexer.FIRST_POSITION)
                                    Shooter.setRPM(0.0)
                                    transition=false
                                }
                            }
                        }
                    }

                }
            }
        }
        else if(gamepadEx2.getButtonDown("a"))
        {
            transition=true
            Indexer.setPosition(Indexer.FIRST_POSITION)
            Shooter.setRPM(4500.0)//4500 for far
            Glider.setPositionDeg(33.0)
            actionQueue.add(800)
            {
                Intake.take()
                actionQueue.add(2200)
                {
                    Intake.stop()
                    Indexer.setPosition(Indexer.SECOND_POSITION)
                    actionQueue.add(200)
                    {
                        Intake.take()
                        actionQueue.add(1800)
                        {   Intake.stop()
                            Indexer.setPosition(Indexer.THIRD_POSITION)
                            actionQueue.add(200)
                            {
                                Intake.take()
                                actionQueue.add(2100)
                                {
                                    Indexer.setPosition(Indexer.FIRST_POSITION)
                                    Shooter.setRPM(0.0)
                                    transition=false
                                }
                            }
                        }
                    }

                }
            }
        }
        if(!transition)
        {

           Shooter.setRPM(0.0)

        }



    }
    val STICK_CONVERTER = 125
    fun handleInputTurret() {

        val stickValue = gamepad1.right_stick_x
        if(gamepad1.right_stick_button)
        {
            Turret.setPower(1.0)
            Turret.setPosition(0)
        }
        else
        {
            if(gamepad1.right_bumper)
            {
                Turret.setPower(0.5)
                Turret.setPosition(Turret.getCurrentPosition()+50)

            }
            else if(gamepad1.left_bumper)
            {
                Turret.setPower(-0.5)
                Turret.setPosition(Turret.getCurrentPosition()-50)

            }
            else if (stickValue > 0.05) {
                val move = (stickValue * STICK_CONVERTER).toInt()
                Turret.setPosition(Turret.getCurrentPosition() + move)
                Turret.setPower(0.7)
            } else if (stickValue < -0.05) {
                val move = (stickValue * STICK_CONVERTER).toInt()
                Turret.setPosition(Turret.getCurrentPosition() + move)
                Turret.setPower(0.7)
            } else {
                Turret.setPower(1.0)
                Turret.setPosition(Turret.getCurrentPosition())
            }
        }






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
            handleInputClamp()
            handleInputIndexer()
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