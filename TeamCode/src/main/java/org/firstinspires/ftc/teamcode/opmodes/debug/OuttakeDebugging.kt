package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Glider
import org.firstinspires.ftc.teamcode.subsystems.Shooter

@TeleOp(name = "OuttakeDebugging")
@Config

class OuttakeDebugging : LinearOpMode() {
    var power = 0.0
    lateinit var log: Log

    override fun runOpMode() {
        val gamepadEx1 = GamepadEx(gamepad1)
        Shooter.init(hardwareMap)
        Glider.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()
        Glider.setPosition(Glider.HIGHER_LIMIT)
        while (opModeIsActive()) {

            if(gamepadEx1.getButtonDown("x"))
                Glider.setPosition(Glider.getPosition()+0.05)

            if(gamepadEx1.getButtonDown("b"))
                Glider.setPosition(Glider.getPosition()-0.05)

            if(gamepadEx1.getButtonDown("dpad_up"))
                power=(power+0.05).coerceIn(-1.0,1.0)
            if(gamepadEx1.getButtonDown("dpad_down"))
                power=(power-0.05).coerceIn(-1.0,1.0)


            if(gamepad1.a)
                Shooter.setPower(power)
            else
                Shooter.setPower(0.0)


            gamepadEx1.update()
            log.add("Current Draw",Shooter.getAverageCurrent())
            log.add("Average Draw per motor",Shooter.getAverageCurrent())
            log.add("Glider Position",Glider.getPosition())
            log.add("Shooter Power", power)



            log.tick()
        }
    }
}