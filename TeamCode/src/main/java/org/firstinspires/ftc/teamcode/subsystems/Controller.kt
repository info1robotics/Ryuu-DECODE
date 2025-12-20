package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.roadrunner.TankDrive.Params
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
        Clamp.init(hardwareMap)
        Indexer.init(hardwareMap)
        Shooter.init(hardwareMap)
        Turret.init(hardwareMap)
        Glider.init(hardwareMap)
        Limelight.init(hardwareMap)
        state = State.INIT
    }

    fun setInit()
    {
        Indexer.setPosition(Indexer.FIRST_POSITION)
        Glider.setPositionDeg(Glider.FAR_DEGREE)
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