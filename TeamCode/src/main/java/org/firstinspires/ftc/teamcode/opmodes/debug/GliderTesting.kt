package org.firstinspires.ftc.teamcode.opmodes.test

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Hood

@TeleOp
@Config

class GliderTesting : LinearOpMode() {
    companion object {
        @JvmField
        var position = 0.0

    }
    lateinit var log: Log
    override fun runOpMode() {
        Hood.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
            Hood.setPosition(position)

            log.add("Glider Position",Hood.getPosition())
            log.tick()
        }
    }
}