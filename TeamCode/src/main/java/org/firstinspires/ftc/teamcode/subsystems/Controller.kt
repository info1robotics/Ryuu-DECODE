package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

object Controller {
    enum class State{ UNKNOWN,
        INIT,
        VISION,
        LOCKED
    }
    lateinit var state:State

    fun init(hardwareMap: HardwareMap) {
        Drivetrain.init(hardwareMap)
        Intake.init(hardwareMap)
        Shooter.init(hardwareMap)
        Turret.init(hardwareMap)
        Hood.init(hardwareMap)
        Limelight.init(hardwareMap)
        Joint.init(hardwareMap)
        Jack.init(hardwareMap)
        state = State.INIT
    }

    fun setInit()
    {
        Hood.setPositionDeg(40.0)
        state = State.INIT
    }

    fun setInitAuto()
    {
        state = State.INIT
    }

    fun setVision()
    {
        state = State.VISION
    }
    fun setLock()
    {

    }



}