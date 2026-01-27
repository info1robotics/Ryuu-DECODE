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
class AutoCloseRedClasifier : AutoBase(Pose(120.0,123.0, 32.0),Colours.RED) {
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
                actionQueue.add(200)
                {
                    Shooter.setRPM(power)
                    Intake.setPowerMain(1.0)
                    Intake.setPowerSupport(1.0)
                    actionQueue.add(300)
                    {
                        Shooter.setRPM(power)
                        actionQueue.add(600)
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
            execute{ goTo(85.0,93.0,38.0)},//preload-1
            execute{Shooter.charge()},
            execute{Intake.setPowerMain(0.7)},
            execute{Turret.setPosition(0.525)},
            sleepms(1100),
            shootSeq,
            sleepms(900),

            execute{ goTo(90.0,63.2,0.0)},//pre collect -2
            preCollectSeq,
            sleepms(1000),
            execute{ goTo(112.0,63.2,0.0)},//collect
            sleepms(1000),
            afterCollectSeq,
            execute{Turret.setPosition(0.757)},
            execute{Shooter.charge()},
            sleepms(300),
            execute{ goTo(84.0,83.0,0.0)},//shoot
            sleepms(1200),
            shootSeq,//TODO calibrate the gate position y

            sleepms(900),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },
            execute{ goTo(90.0,62.9,0.0)},//
            sleepms(1300),
            execute{ goTo(115.6,62.9,0.0)},//collect
            preCollectSeq,
            sleepms(700),
            execute{ goTo(120.3,59.0,30.0)},//push gate
            sleepms(1500),//wait at gate
            execute{Shooter.charge()},
            execute{ goTo(84.0,83.0,0.0)},
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.1)},
            sleepms(1300),
            shootSeq,

            sleepms(900),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },//spike mark
            execute{ goTo(98.0,83.9,0.0)} ,//pre collect -4
            preCollectSeq,
            sleepms(500),
            execute{ goTo(120.0,83.9,0.0)},//collect
            sleepms(750),
            afterCollectSeq,
            execute{Shooter.charge()},
            execute{ goTo(84.0,83.0,0.0)},
            sleepms(1200),
            shootSeq,


            sleepms(900),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },
            execute{ goTo(90.0,62.9,0.0)},//
            sleepms(1300),
            execute{ goTo(115.6,62.9,0.0)},//collect
            preCollectSeq,
            sleepms(700),
            execute{ goTo(120.3,59.0,30.0)},//push gate
            sleepms(1500),//wait at gate
            execute{Shooter.charge()},
            execute{ goTo(84.0,83.0,0.0)},
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.1)},
            sleepms(1300),
            shootSeq,

            sleepms(900),
            execute { goTo(92.0, 38.5, 0.0) }, // last spike mark -6
            preCollectSeq,
            sleepms(1900),
            execute { goTo(122.0, 38.5, 0.0) },//collected
            sleepms(700),
            afterCollectSeq,
            execute{ goTo(84.0,83.0,0.0)},
            sleepms(100),
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.1)},
            sleepms(1100),
            execute{Shooter.charge()},
            execute{Joint.setPosition(Joint.COLLECT_POSITION)},
            sleepms(700),
            shootSeq,
            sleepms(900),
            execute{ goTo(98.0,70.0,0.0)},


            sleepms(999999999),






            )
    }

}