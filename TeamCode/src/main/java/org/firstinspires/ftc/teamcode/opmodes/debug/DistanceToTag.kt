package org.firstinspires.ftc.teamcode.opmodes.debug

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp
class DistanceToTag: LinearOpMode() {
    lateinit var log: Log
    override fun runOpMode() {

        Limelight.init(hardwareMap,0)
        Limelight.start()
        log = Log(this.telemetry)
        log.tick()

        waitForStart()

        while (opModeIsActive() && !isStopRequested)
        {
            var ta = Limelight.getTa()
            var distance = ta?.let { Limelight.getDistanceToAprilTag(it) }


            Limelight.getTx()?.let { log.add("tx", it) }
            Limelight.getTa()?.let { log.add("ta", it) }
            log.add(distance.toString())
            log.tick()

        }
        Limelight.stop()
    }
}