package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Wicket

@TeleOp
@Config
class WicketTesting : LinearOpMode() {
    private val actionQueue = ActionQueue()
    companion object {
        @JvmField
        var position = Wicket.CLOSE_POSITION
    }
    lateinit var log: Log
    override fun runOpMode() {
        Wicket.init(hardwareMap)
        log = Log(this.telemetry)
        waitForStart()

        while (opModeIsActive()) {
            Wicket.setPosition(position)
            log.add("Wicket Position",Wicket.getPosition())
            log.tick()
        }
    }
}