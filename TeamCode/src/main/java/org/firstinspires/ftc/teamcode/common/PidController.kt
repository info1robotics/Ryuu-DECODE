package org.firstinspires.ftc.teamcode.common

class PidController(val kP: Double, val kI: Double, val kD: Double) {
    private var lastError = 0.0
    private var integral = 0.0

    fun calculate(target: Double, current: Double): Double {
        val error = target - current
        integral += error
        val derivative = error - lastError
        lastError = error
        return (kP * error) + (kI * integral) + (kD * derivative)
    }
}