package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PIDFCoefficients
import com.qualcomm.robotcore.hardware.VoltageSensor
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import kotlin.math.pow

object Shooter {
    private lateinit var motorShooterFirst: DcMotorEx // 6000 RPM goBILDA (≈8400 RPM after 1.4x gear)
    private lateinit var motorShooterSecond: DcMotorEx
    private lateinit var voltageSensor: VoltageSensor

    private const val MOTOR_TICKS_PER_REV = 28
    private const val MAX_RPM = 5800.0

    private val BASE_PIDF = PIDFCoefficients(0.005, 0.0003, 0.0001, 12.0) // Base feedforward at 12V

    private val distanceRpmTable = listOf(
        0.5 to 3120.0,
        1.0 to 3300.0,
        1.5 to 3650.0,
        2.0 to 3900.0,
        2.5 to 4000.0,
        3.0 to 4100.0
    )

    fun init(hardwareMap: HardwareMap) {
        motorShooterFirst = hardwareMap.get(DcMotorEx::class.java, "motorShooterFirst")
        motorShooterSecond = hardwareMap.get(DcMotorEx::class.java, "motorShooterSecond")

        motorShooterFirst.direction = DcMotorSimple.Direction.REVERSE
        motorShooterSecond.direction = DcMotorSimple.Direction.REVERSE

        val config = motorShooterFirst.motorType.clone()
        config.achieveableMaxRPMFraction = 1.0
        motorShooterFirst.motorType = config
        motorShooterSecond.motorType = config

        voltageSensor = hardwareMap.voltageSensor.iterator().next()

        applyPIDFCoefficients(BASE_PIDF)
    }

    private fun applyPIDFCoefficients(base: PIDFCoefficients) {
        val compensatedF = base.f * (12.0 / voltageSensor.voltage)
        val compensated = PIDFCoefficients(base.p, base.i, base.d, compensatedF)
        motorShooterFirst.setVelocityPIDFCoefficients(
            compensated.p,
            compensated.i,
            compensated.d,
            compensated.f
        )
        motorShooterSecond.setVelocityPIDFCoefficients(
            compensated.p,
            compensated.i,
            compensated.d,
            compensated.f
        )
    }

    fun setRPM(rpm: Double) {
        val targetVelocityTicksPerSec = (rpm * MOTOR_TICKS_PER_REV) / 60.0
        applyPIDFCoefficients(BASE_PIDF)
        motorShooterFirst.velocity = targetVelocityTicksPerSec
        motorShooterSecond.velocity = targetVelocityTicksPerSec
    }

    fun getRPM(): Double {
        val ticksPerSec = motorShooterFirst.velocity
        return (ticksPerSec / MOTOR_TICKS_PER_REV) * 60.0
    }

    fun setVelocityForDistance(distance: Double) {
        val rpm = getRpmForDistance(distance)
        setRPM(rpm)
    }

    private fun getRpmForDistance(distance: Double): Double {
        val clamped = distance.coerceIn(
            distanceRpmTable.first().first,
            distanceRpmTable.last().first
        )
        val lower = distanceRpmTable.lastOrNull { it.first <= clamped } ?: distanceRpmTable.first()
        val upper = distanceRpmTable.firstOrNull { it.first >= clamped } ?: distanceRpmTable.last()

        return if (lower == upper) lower.second
        else {
            val (d1, rpm1) = lower
            val (d2, rpm2) = upper
            rpm1 + (clamped - d1) * (rpm2 - rpm1) / (d2 - d1)
        }
    }

    fun stop() {
        motorShooterFirst.power = 0.0
        motorShooterSecond.power = 0.0
    }
    fun getCurrentDraw(): Double =
        motorShooterFirst.getCurrent(CurrentUnit.AMPS) + motorShooterSecond.getCurrent(CurrentUnit.AMPS)

    fun getAverageCurrent(): Double =
        (motorShooterFirst.getCurrent(CurrentUnit.AMPS) + motorShooterSecond.getCurrent(CurrentUnit.AMPS)) / 2.0

    fun setPower(power: Double) {

        motorShooterFirst.power = power
        motorShooterSecond.power = power
    }
    fun getPower(): Double {
        return motorShooterFirst.power
    }

}