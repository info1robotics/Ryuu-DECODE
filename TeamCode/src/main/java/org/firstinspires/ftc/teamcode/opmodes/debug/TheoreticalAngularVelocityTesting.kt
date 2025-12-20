package org.firstinspires.ftc.teamcode.opmodes.debug

import org.firstinspires.ftc.teamcode.common.Alfa
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Shooter

@TeleOp
@Config

class TheoreticalAngularVelocityTesting : LinearOpMode() {

    val power = 0.0
    val DISTANCE = 2.0//m
    var CURRENT_RPS = 6000.0
    lateinit var log: Log

    override fun runOpMode() {
        val gamepadEx1 = GamepadEx(gamepad1)
        val alfa = Alfa()

        Shooter.init(hardwareMap)
        Glider.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()
        Glider.setPosition(Glider.HIGHER_LIMIT)
        while (opModeIsActive()) {
            //CURRENT_RPS = Shooter.powerToRPM(1.0)/60

            alfa.calculate(DISTANCE,CURRENT_RPS)

            log.add(alfa.final_alfa.toString(), "final angle")


            log.tick()
        }
    }
}