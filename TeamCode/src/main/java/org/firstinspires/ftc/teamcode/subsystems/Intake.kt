package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

object Intake {

    lateinit var motorIntake:DcMotor
    private lateinit var sensorIntake: RevColorSensorV3
    private var colour = "Red"//TODO change the colour that you want to collect

    fun init(hardwareMap: HardwareMap) {
        motorIntake = hardwareMap.get(DcMotor::class.java, "motorIntake")
        motorIntake.direction = DcMotorSimple.Direction.REVERSE
        val motorConfigurationType = motorIntake.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0
        motorIntake.motorType = motorConfigurationType
    }

    fun setPower(power: Double) {
        motorIntake.power = power
    }

    fun stop() {
        motorIntake.power = 0.0
    }

    fun reverse() {
        motorIntake.power = -0.8
    }

    fun take() {
        motorIntake.power = 0.8
    }


}