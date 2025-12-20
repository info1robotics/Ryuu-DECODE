package org.firstinspires.ftc.teamcode.opmodes.debug

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
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

            Limelight.getTa()?.let {
                Limelight.getDistanceToAprilTag(
                    it
                ).toString()
            }?.let {
                log.add("distance",
                    it
                )
            }

            Limelight.getTx()?.let { log.add("tx", it) }
            Limelight.getTa()?.let { log.add("ta", it) }

        }
        Limelight.stop()
    }
}