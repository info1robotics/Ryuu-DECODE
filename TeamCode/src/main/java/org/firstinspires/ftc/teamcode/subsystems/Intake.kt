package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

object Intake {

    lateinit var motorIntakeMain:DcMotor
    lateinit var motorIntakeSupport:DcMotor


    fun init(hardwareMap: HardwareMap) {
        motorIntakeMain = hardwareMap.get(DcMotor::class.java, "motorIntakeMain")
        motorIntakeSupport = hardwareMap.get(DcMotor::class.java, "motorIntakeSupportSupport")


        motorIntakeMain.direction = DcMotorSimple.Direction.REVERSE
        motorIntakeSupport.direction = DcMotorSimple.Direction.FORWARD

        val motorConfigurationType = motorIntakeMain.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0

        motorIntakeMain.motorType = motorConfigurationType
        motorIntakeSupport.motorType = motorConfigurationType

    }

    fun setPower(power: Double) {
        motorIntakeMain.power = power
        motorIntakeSupport.power = power
    }

    fun stop() {
        motorIntakeMain.power = 0.0
        motorIntakeSupport.power = 0.0
    }

    fun reverse() {
        motorIntakeMain.power = -0.8
        motorIntakeSupport.power = -0.8
    }

    fun take() {
        motorIntakeMain.power = 0.8
        motorIntakeSupport.power = 0.8
    }


}