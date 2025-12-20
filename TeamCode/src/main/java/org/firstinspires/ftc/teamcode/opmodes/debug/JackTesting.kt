package org.firstinspires.ftc.teamcode.opmodes.test

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Jack
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp
@Config
class JackTesting : LinearOpMode() {
    companion object {
        @JvmField
        var position = 0.0

    }
    lateinit var log: Log
    override fun runOpMode() {
        Jack.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
            Jack.setPosition(position)

            log.add("Jack Position",Jack.getPosition())
            log.tick()
        }
    }
}