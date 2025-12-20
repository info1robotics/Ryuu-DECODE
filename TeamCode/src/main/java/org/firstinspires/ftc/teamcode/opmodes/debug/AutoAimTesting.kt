package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Shooter

@TeleOp
@Config
class AutoAimTesting : LinearOpMode() {
    companion object {
        @JvmField
        var deg = 0.0
        @JvmField
        var rpm = 0.0
        @JvmField
        var distance = 0.0
        @JvmField
        var tuning = true
    }

    private lateinit var log: Log

    override fun runOpMode() {
        Shooter.init(hardwareMap)
        Glider.init(hardwareMap)
        log = Log(telemetry)
        waitForStart()


        while (opModeIsActive()) {
            if(tuning)
            {
                Shooter.setRPM(rpm)
                Glider.setPositionDeg(deg)
            }
            else
            {
                Glider.autoAim(distance)
                Shooter.setVelocityForDistance(distance)
            }
            log.add("if the tuning variable is true then you manually change the rpm and degrees" +
                    "use this when editing the tables")
            log.add("if it s false then you just give the distance and it calculates " +
                    "the power and degree automatically use this to check if the trajectory is accurate")
            log.add("Shooter Power", Shooter.getPower())
            log.add("Shooter RPM",Shooter.getRPM())
            log.tick()
        }

        Shooter.stop()
    }
}