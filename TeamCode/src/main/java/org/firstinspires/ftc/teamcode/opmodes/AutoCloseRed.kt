package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.common.AutoUtil.p
import org.firstinspires.ftc.teamcode.common.AutoUtil.rad
import org.firstinspires.ftc.teamcode.enums.Colours
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Hood
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
import org.firstinspires.ftc.teamcode.tasks.Task
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.action
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.execute
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepms
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepuntil

@Autonomous

class AutoCloseRed : AutoBase(Pose(123.0,120.0, 32.0)) {

    fun turnTo(degrees: Double) { // if you want to turn right, use negative degrees
        val temp = Pose(follower.pose.x, follower.pose.y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }

    fun goTo(x: Double, y: Double, degrees: Double) {
        val temp = Pose(x, y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }
    fun stopMidTrajectory() {
        follower.holdPoint(follower.pose)
    }
    private val shootSeq = serial(
        execute{
            Shooter.charge()
            Hood.setPosition(deg)
            actionQueue.add(600)
            {
                Shooter.setRPM(power)
                actionQueue.add(100)
                {
                    Intake.setPower(0.7)
                    actionQueue.add(600)
                    {
                        Shooter.setRPM(0.0)
                        Intake.stop()
                    }
                }
            }

        }
    )
    private val preCollectSeq = serial(
        execute{Joint.setPosition(Joint.COLLECT_POSITION)},
        execute{Intake.setPowerMain(0.8)} ,
        execute{Intake.setPowerSupport(0.7)},
    )
    private val collectSeq = serial(
        sleepms(300),
        execute{Intake.setPowerSupport(0.2)},
        sleepms(800),
        execute{Intake.setPowerSupport(0.0)},
        sleepms(500),
        execute{Intake.setPowerMain(0.0)},
        execute{Joint.setPosition(Joint.INIT_POSITION) }
    )

    override fun onInit(){
        super.onInit()
        //allianceColour=  Colours.RED//TODO change for each auto


        task = serial(
            execute{ goTo(90.0,90.0,45.0)},
            sleepms(900),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },
            shootSeq,
            sleepms(1300),
            execute{ goTo(98.0,80.0,0.0)},
            preCollectSeq,
            sleepms(500),
            execute{ goTo(123.0,80.0,0.0)},
            collectSeq,
            execute{ goTo(80.0,80.0,45.0)},
            sleepms(700),
            shootSeq,
            sleepms(1300),
            execute{ goTo(90.0,57.0,0.0)},
            preCollectSeq,
            sleepms(500),
            execute{ goTo(123.0,57.0,0.0)},
            collectSeq,
            execute{ goTo(85.0,85.0,45.0)},
            sleepms(1000),
            shootSeq,
            sleepms(1300),
        )
    }

}