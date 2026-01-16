package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
@Config
@TeleOp
class TurretTesting : LinearOpMode() {
    companion object {
        @JvmField
        var position = 0.5

    }
    lateinit var log: Log
    override fun runOpMode() {
        Turret.init(hardwareMap)
        Limelight.init(hardwareMap,0)
        Limelight.start()
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
            var tx = Limelight.getTx()
            //Turret.setPosition(position)
            Turret.setPosition(position)

            Limelight.getTx()?.let { log.add("tx", it) }
            log.add("Turret position",Turret.getPosition())
            log.tick()
        }
    }
}