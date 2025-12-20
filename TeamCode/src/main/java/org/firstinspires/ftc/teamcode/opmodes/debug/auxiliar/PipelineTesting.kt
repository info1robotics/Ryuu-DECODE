package org.firstinspires.ftc.teamcode.opmodes.debug.auxiliar

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.AprilTags
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp(name = "Limelight AprilTag Test", group = "Test")
class PipelineTesting : LinearOpMode() {
    lateinit var log: Log

    override fun runOpMode() {
        Limelight.init(hardwareMap, 0)
        Limelight.start()
        Limelight.changePipeline(0)

        log = Log(this.telemetry)
        log.add("Limelight initialized. Waiting for start...")
        log.tick()

        waitForStart()

        while (opModeIsActive()) {
            val tagId = Limelight.getAprilTagId()
            log.tick()

            when (tagId) {
                AprilTags.GPP -> log.add("GPP")
                AprilTags.PGP -> log.add("PGP")
                AprilTags.PPG -> log.add("PPG")
                AprilTags.BLUE -> log.add("Blue alliance basket")
                AprilTags.RED -> log.add("Red alliance basket")
                else -> log.add("No id detected")
            }
            val dist = Limelight.getDistanceToAprilTag(.75, 0.5, 0.0)
            log.add(Limelight.getTx().toString())
            log.add(Limelight.getTy().toString())
            log.add(dist.toString())
            log.add(Limelight.isCentered().toString())
            log.tick()
        }

        Limelight.stop()
    }
}