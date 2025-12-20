package org.firstinspires.ftc.teamcode.opmodes.debug

import org.firstinspires.ftc.teamcode.common.Alfa
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp
@Config

class PhysicalAngularVelocityTesting : LinearOpMode() {
    //TODO get distance from the limelight

    var power = 0.0
    val DISTANCE = 0.0//m
    var CURRENT_RPS = 0.0
    lateinit var log: Log

    override fun runOpMode() {
        val gamepadEx1 = GamepadEx(gamepad1)
        val alfa = Alfa()
        Limelight.init(hardwareMap,0)
        Limelight.start()
        Shooter.init(hardwareMap)
        Glider.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()
        Glider.setPosition(Glider.HIGHER_LIMIT)
        while (opModeIsActive()) {
            //CURRENT_RPS = Shooter.powerToRPM(1.0)/60

            alfa.calculate(DISTANCE,CURRENT_RPS)

            log.add("Current Draw",Shooter.getCurrentDraw())
            log.add("Average Draw per motor",Shooter.getAverageCurrent())
            log.add("Glider Position",Glider.getPosition())
            log.add("Shooter Power", power)

            log.add("final angle", alfa.final_alfa)


            log.tick()
        }
    }
}