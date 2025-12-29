package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Hood
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

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
        var intakePower = 0.0
        @JvmField
        var tuning = true

    }

    private lateinit var log: Log

    override fun runOpMode() {
        Shooter.init(hardwareMap)
        Joint.init(hardwareMap)
        Hood.init(hardwareMap)
        Intake.init(hardwareMap)

        Limelight.init(hardwareMap,0)
        Limelight.start()
        log = Log(telemetry)
        waitForStart()


        while (opModeIsActive()) {

            var ta = Limelight.getTa()
            var distanceLL = ta?.let { Limelight.getDistanceToAprilTag(it) }
            Joint.setPosition(Joint.COLLECT_POSITION)
            if(tuning)
            {

                Shooter.setRPM(rpm)
                Hood.setPosition(deg)
            }
            else
            {
                if (distanceLL != null) {
                    Hood.setPosition(Hood.calculate(distanceLL))
                }
                if (distanceLL != null) {
                    Shooter.setRPM(Shooter.calculate(distanceLL))
                }

            }
            Intake.setPower(intakePower)
            log.add("if the tuning variable is true then you manually change the rpm and degrees" +
                    "use this when editing the tables")
            log.add("if it s false then you just give the distance and it calculates " +
                    "the power and degree automatically use this to check if the trajectory is accurate")
            log.add("Shooter Power", Shooter.getPower())
            log.add("Shooter RPM",Shooter.getRPM())



            Limelight.getTx()?.let { log.add("tx", it) }
            Limelight.getTa()?.let { log.add("ta", it) }
            log.add(distanceLL.toString())
            log.tick()
        }

        Shooter.stop()
    }
}