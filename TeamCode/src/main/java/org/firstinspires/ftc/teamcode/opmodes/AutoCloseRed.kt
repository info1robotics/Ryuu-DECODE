package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
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
@Disabled
@Autonomous
class AutoCloseRed : AutoBase(Pose(120.0,123.0, 32.0),Colours.RED) {
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
            Intake.stop()
                Shooter.setRPM(power)
                actionQueue.add(100)
                {
                    Wicket.setPosition(Wicket.OPEN_POSITION)
                    actionQueue.add(300)
                    {
                        Shooter.setRPM(power)
                        Intake.setPowerMain(1.0)
                        Intake.setPowerSupport(0.7)
                        actionQueue.add(400)
                        {
                            Shooter.setRPM(power)

                            actionQueue.add(700)
                            {
                                Shooter.setRPM(0.0)
                                Wicket.setPosition(Wicket.CLOSE_POSITION)
                            }
                        }

                    }
                }
        }
    )
    private val preCollectSeq = serial(
        execute{Joint.setPosition(Joint.COLLECT_POSITION)},
        execute{Intake.setPowerMain(1.0)} ,
        execute{Intake.setPowerSupport(0.9)},
    )
    private val afterCollectSeq = serial(
        execute{
            Intake.setPowerMain(1.0)
            Joint.setPosition(Joint.INIT_POSITION)
        }
    )

    override fun onInit(){
        super.onInit()
        far=false

        task = serial(
            execute{ goTo(88.0,93.0,45.0)},//preload-1
            execute{Shooter.charge()},
            execute{Intake.setPowerMain(0.7)},
            sleepms(1000),
            shootSeq,
            sleepms(1100),
            execute{ goTo(90.0,62.0,0.0)},//pre collect -2
            preCollectSeq,
            sleepms(1000),
            execute{ goTo(115.0,62.0,0.0)},//collect
            sleepms(900),
            afterCollectSeq,
            execute{Turret.setPosition(0.757)},
            execute{Shooter.charge()},
            sleepms(300),
            execute{ goTo(85.0,83.0,0.0)},//shoot
            sleepms(1100),
            shootSeq,//TODO calibrate the gate position y

            sleepms(1000),
            execute{ goTo(115.6,60.0,11.5)},//collect -3
            sleepms(800),
            preCollectSeq,
            execute{ goTo(126.0,60.0,11.5)},//push gate
            sleepms(2200),//wait at gate
            execute{Shooter.charge()},
            execute{ goTo(85.0,83.0,0.0)},
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.2)},
            sleepms(1800),
            shootSeq,

            sleepms(1100),
            execute{ goTo(115.6,60.0,11.5)},//collect -4
            sleepms(800),
            preCollectSeq,
            execute{ goTo(126.0,60.0,11.5)},//push gate
            sleepms(3000),//wait at gate
            execute{Shooter.charge()},
            execute{ goTo(85.0,83.0,0.0)},
            sleepms(1000),
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.2)},
            sleepms(800),
            shootSeq,

            sleepms(1100),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },
            execute{ goTo(98.0,83.6,0.0)} ,//pre collect -5
            preCollectSeq,
            sleepms(500),
            execute{ goTo(119.0,83.6,0.0)},//collect
            sleepms(750),
            afterCollectSeq,
            execute{Shooter.charge()},
            execute{ goTo(85.0,83.0,0.0)},
            sleepms(950),
            shootSeq,

            sleepms(1100),
            execute{ goTo(115.6,60.0,11.5)},//collect -6
            sleepms(800),
            preCollectSeq,
            execute{ goTo(126.0,60.0,11.5)},//push gate
            sleepms(2000),//wait at gate
            execute{Shooter.charge()},
            execute{ goTo(85.0,83.0,0.0)},
            sleepms(600),
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.2)},
            sleepms(1000),
            shootSeq,

            sleepms(900),
            execute{ goTo(85.0,70.0,0.0)},


            sleepms(999999999),






            )
    }

}