package org.firstinspires.ftc.teamcode.opmodes.test

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Jack
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp
@Config
class IntakeTesting : LinearOpMode() {
    companion object {
        @JvmField
        var power = 0.0

    }
    lateinit var log: Log
    override fun runOpMode() {
        Intake.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
            Intake.setPower(power)
            log.add("Intake Power",Intake.motorIntakeMain.power)
            log.tick()
        }
    }
}