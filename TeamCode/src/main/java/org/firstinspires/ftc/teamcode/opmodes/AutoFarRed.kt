package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.enums.Colours
import org.firstinspires.ftc.teamcode.subsystems.Hood
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.Wicket
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.execute
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepms

@Autonomous
class AutoFarRed : AutoBase(Pose(90.0, 9.0, Math.toRadians(90.0)),Colours.RED) {

    fun turnTo(degrees: Double) {
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
        execute {
            Intake.stop()
            Shooter.setRPM(2900.0)
            Hood.setPosition(0.68)
            actionQueue.add(100) {
                Wicket.setPosition(Wicket.OPEN_POSITION)
                actionQueue.add(300) {
                    Intake.setPowerMain(1.0)
                    Intake.setPowerSupport(0.7)
                    actionQueue.add(700)
                    {
                        actionQueue.add(600) {
                            Shooter.setRPM(0.0)
                            Wicket.setPosition(Wicket.CLOSE_POSITION)
                        }
                    }

                }

            }
        }
    )

    private val preCollectSeq = serial(
        execute { Joint.setPosition(Joint.COLLECT_POSITION) },
        execute { Intake.setPowerMain(1.0) },
        execute { Intake.setPowerSupport(1.0) },
    )

    private val afterCollectSeq = serial(
        execute {
            Intake.setPowerMain(1.0)
            Joint.setPosition(Joint.INIT_POSITION)
        }
    )
    private val charge = serial(
        execute{
            Shooter.setRPM(2900.0)
        }
    )

    override fun onInit() {
        super.onInit()
        far=true
        task = serial(
            execute{Wicket.setPosition(Wicket.CLOSE_POSITION)},//preload -1
            charge,
            execute{Turret.setPosition(0.372)},
            execute{goTo(90.0,16.0,90.0)},
            sleepms(1200),
            shootSeq,
            sleepms(2200),
            //sleepms(25000)
            execute { goTo(105.0, 34.0, 0.0) }, // first spike mark -2
            sleepms(600),
            preCollectSeq,
            execute { goTo(130.0, 34.0, 0.0) },//collected
            sleepms(1200),
            afterCollectSeq,
            charge,

            execute{goTo(90.0,16.0,90.0)},//shooting position
            sleepms(2500),
            shootSeq,
            sleepms(2200),

            preCollectSeq,
            execute{goTo(140.0,8.0,0.0)},//human player -3
            sleepms(2000),
            execute{goTo(125.0,8.0,0.0)},
            sleepms(1000),
            execute{goTo(140.0,8.0,0.0)},
            sleepms(1800),
            charge,
            execute{goTo(90.0,16.0,90.0)},
            sleepms(700),
            afterCollectSeq,
            execute{Joint.setPosition(Joint.INIT_POSITION+0.1)},
            sleepms(1900),
            shootSeq,

            sleepms(2000),
            execute{goTo(140.0,8.0,30.0)},//human player -4
            preCollectSeq,
            sleepms(400),
            execute{goTo(139.0,9.0,70.0)},//human player -4
            sleepms(800),
            execute{goTo(139.0,40.0,70.0)},
            sleepms(2500),
            charge,
            execute{goTo(90.0,16.0,90.0)},//shooting position
            sleepms(700),
            execute{Joint.setPosition(Joint.INIT_POSITION+0.1)},
            sleepms(1900),
            shootSeq,
            sleepms(2200),
            execute{goTo(90.0,30.0,90.0)},
            sleepms(999999999)
        )
    }
}