package org.firstinspires.ftc.teamcode.opmodes.debug.auxiliar

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log

@TeleOp(name = "OpMode Test", group = "Test")
class OpModeTesting : LinearOpMode() {
    lateinit var log: Log
    override fun runOpMode() {

        log = Log(this.telemetry)
        log.add("raco", "Started")
        log.tick()
        waitForStart()

        while (opModeIsActive()) {
            // Keep telemetry updating if needed
            log.add("Status", "six sevenn")
            log.tick()
        }
    }
}
