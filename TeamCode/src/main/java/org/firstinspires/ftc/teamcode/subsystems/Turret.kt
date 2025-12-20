package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.AprilTags
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
import kotlin.math.absoluteValue
import kotlin.math.sign

object Turret {

    private const val TICKS_PER_REV = 1600 * 6 // Gearbox 1:6
    private const val TICKS_PER_DEG = TICKS_PER_REV / 360.0

    const val RIGHT_LIMIT = 180
    const val LEFT_LIMIT = -370

    lateinit var motorTurret: DcMotorEx
    private var lastError = 0.0

    private val deadZoneDeg = 1.0 // When close enough to centered

    fun init(hardwareMap: HardwareMap) {
        motorTurret = hardwareMap.get(DcMotorEx::class.java, "motorTurret")
        motorTurret.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        motorTurret.targetPosition = 0
        motorTurret.mode = DcMotor.RunMode.RUN_TO_POSITION
    }

    fun resetEncoder() {
        motorTurret.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motorTurret.targetPosition = 0
        motorTurret.mode = DcMotor.RunMode.RUN_TO_POSITION
    }

    fun setPower(power: Double) {
        motorTurret.power = power
    }

    fun hold() {
        motorTurret.power = 0.0
    }
    fun default()
    {
        motorTurret.power=1.0
        motorTurret.targetPosition = 0
    }
    fun setPosition(target: Int) {
        val current = getCurrentPosition()
        // val power = pidController.calculate(target.toDouble(), current.toDouble()).coerceIn(-1.0, 1.0)
        motorTurret.targetPosition = target.coerceIn(LEFT_LIMIT, RIGHT_LIMIT)
    }
    fun getTargetPosition(): Int {
        return motorTurret.targetPosition
    }
    fun getCurrentPosition():Int{
        return motorTurret.currentPosition
    }

    fun withoutEncoder() {
        motorTurret.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
    private var lastTx = 0.0
    private var lastTime = System.currentTimeMillis()

    fun lock() {
        val tx = Limelight.getTx() ?: return // nothing detected -> do nothing

        // Tuning constant — adjust based on behavior
        val kP = 3.0 // degrees of turret movement per 1° of limelight error

        // Convert Limelight horizontal error → turret movement (in degrees)
        val turretDeltaDeg = tx * kP

        if (turretDeltaDeg.absoluteValue <= deadZoneDeg) {
            hold()
            return
        }

        val currentPos = getCurrentPosition()
        val currentDeg = currentPos / TICKS_PER_DEG
        val targetDeg = currentDeg + turretDeltaDeg

        val targetTicks = (targetDeg * TICKS_PER_DEG).toInt()
            .coerceIn((LEFT_LIMIT * TICKS_PER_DEG).toInt(), (RIGHT_LIMIT * TICKS_PER_DEG).toInt())

        motorTurret.targetPosition = targetTicks
        motorTurret.mode = DcMotor.RunMode.RUN_TO_POSITION
        motorTurret.power = 0.5 // adjust for speed
    }

    const val TICKS_PER_DEGREE = 12  // <-- update this for YOUR turret
}