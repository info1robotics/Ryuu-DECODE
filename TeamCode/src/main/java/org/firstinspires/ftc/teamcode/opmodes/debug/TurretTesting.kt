package org.firstinspires.ftc.teamcode.opmodes.test

import android.net.ipsec.ike.TunnelModeChildSessionParams.TunnelModeChildConfigRequest
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
        var position = 0
        @JvmField
        var reset = false

    }
    lateinit var log: Log
    override fun runOpMode() {
        Limelight.init(hardwareMap)
        Turret.init(hardwareMap)
        Limelight.start()
        Limelight.changePipeline(0)
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
           // Turret.setPower(0.5)
            Turret.lock()
            //Turret.setPosition(position)
            if(reset)
            {
                Turret.resetEncoder()
            }
           // log.add("Tx",Limelight.getTx().toString())
            log.add("Motor Power", Turret.motorTurret.power)
            log.add("target position",Turret.getCurrentPosition())
            log.tick()
        }
    }
}