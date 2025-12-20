package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
@Disabled
@TeleOp(name = "RPM Testing", group = "Debug")
@Config
class RPMTesting : LinearOpMode() {
    companion object {
        @JvmField
        var requestedPower = 0.0
    }

    private lateinit var motor: DcMotorEx
    private val runtime = ElapsedTime()

    override fun runOpMode() {
        motor = hardwareMap.get(DcMotorEx::class.java, "motorShooterFirst")

        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        val ticksPerRev = try {
            motor.motorType.ticksPerRev.toDouble()
        } catch (e: Exception) {
            1.0
        }

        telemetry.addLine("Ready. Adjust requestedPower in dashboard or code.")
        telemetry.update()

        waitForStart()
        runtime.reset()

        while (opModeIsActive()) {
            motor.power = requestedPower

            val ticksPerSec = motor.velocity
            val rpm = if (ticksPerRev > 0.0) (ticksPerSec / ticksPerRev) * 60.0 else Double.NaN
            val freeSpeedRpm = 6000.0
            val percentOfFree = if (!rpm.isNaN()) (rpm / freeSpeedRpm) * 100.0 else Double.NaN

            telemetry.addData("Power", "%.2f".format(requestedPower))
            telemetry.addData("Velocity (ticks/s)", "%.1f".format(ticksPerSec))
            telemetry.addData("Measured RPM", if (!rpm.isNaN()) "%.1f rpm".format(rpm) else "unknown")
            telemetry.addData("Of 6000 RPM", if (!percentOfFree.isNaN()) "%.1f %%".format(percentOfFree) else "unknown")
            telemetry.addData("Encoder CPR", if (ticksPerRev > 1.0) "%.0f".format(ticksPerRev) else "unknown")
            telemetry.addData("Runtime", runtime.toString())
            telemetry.update()
        }

        motor.power = 0.0
    }
}