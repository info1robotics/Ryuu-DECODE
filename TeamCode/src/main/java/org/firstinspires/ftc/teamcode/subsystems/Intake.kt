package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.DigitalChannel
import com.qualcomm.robotcore.hardware.HardwareMap

object Intake {

    lateinit var motorIntakeMain: DcMotor
    lateinit var motorIntakeSupport: DcMotor
    private lateinit var breakBeam: DigitalChannel

    fun init(hardwareMap: HardwareMap) {
        motorIntakeMain = hardwareMap.get(DcMotor::class.java, "motorIntakeMain")
        motorIntakeSupport = hardwareMap.get(DcMotor::class.java, "motorIntakeSupport")

        breakBeam = hardwareMap.get(DigitalChannel::class.java, "sensorIntake")
        breakBeam.mode = DigitalChannel.Mode.INPUT

        motorIntakeMain.direction = DcMotorSimple.Direction.FORWARD
        motorIntakeSupport.direction = DcMotorSimple.Direction.REVERSE

        motorIntakeMain.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        motorIntakeSupport.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        val motorConfigurationType = motorIntakeMain.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0

        motorIntakeMain.motorType = motorConfigurationType
        motorIntakeSupport.motorType = motorConfigurationType
    }

    fun setPower(power: Double) {
        motorIntakeMain.power = power
        motorIntakeSupport.power = power
    }

    fun setPowerMain(power: Double) {
        motorIntakeMain.power = power
    }

    fun setPowerSupport(power: Double) {
        motorIntakeSupport.power = power
    }

    fun getPowerMain(): Double {
        return motorIntakeMain.power
    }

    fun stop() {
        motorIntakeMain.power = 0.0
        motorIntakeSupport.power = 0.0
    }

    fun reverse() {
        motorIntakeMain.power = -1.0
        motorIntakeSupport.power = -1.0
    }

    fun take() {
        motorIntakeMain.power = 1.0
        motorIntakeSupport.power = 1.0
    }

    fun takeMain() {
        motorIntakeMain.power = 1.0
    }

    fun takeSupport() {
        motorIntakeSupport.power = 1.0
    }

    /**
     * @return true if intake is empty (beam NOT broken)
     */
    fun isEmpty(): Boolean {
        return breakBeam.state
    }

    /**
     * @return true if object is detected (beam broken)
     */
    fun hasObject(): Boolean {
        return !breakBeam.state
    }
}