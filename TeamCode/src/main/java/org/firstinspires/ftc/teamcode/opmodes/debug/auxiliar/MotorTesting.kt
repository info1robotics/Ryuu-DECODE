package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.common.Log

@Config
@TeleOp
class MotorTesting : LinearOpMode() {

    companion object {
        @JvmField var motorPower = 0.0
        @JvmField var encoder = false
        @JvmField var motor = "motor"
    }

    override fun runOpMode() {
        val log = Log(telemetry)
        val motor = hardwareMap.dcMotor.get(motor)

        if (encoder) {
            motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        } else {
            motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }

        var lastPosition = motor.currentPosition
        var lastTime = System.nanoTime()

        waitForStart()

        while (opModeIsActive() && !isStopRequested) {
            motor.power = motorPower

            val currentPosition = motor.currentPosition
            val currentTime = System.nanoTime()

            val deltaTime = (currentTime - lastTime) / 1e9
            val deltaPosition = currentPosition - lastPosition

            val velocity = deltaPosition / deltaTime

            log.add("Power", motor.power)
            log.add("Position", currentPosition)
            log.add("Velocity (tps)", "%.2f".format(velocity))

            log.tick()

            lastPosition = currentPosition
            lastTime = currentTime
        }
    }
}
