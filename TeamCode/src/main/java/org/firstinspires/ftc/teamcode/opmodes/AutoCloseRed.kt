package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
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
            Intake.stop()
            Hood.setPosition(deg)
                Shooter.setRPM(power)
                actionQueue.add(100)
                {
                    Wicket.setPosition(Wicket.OPEN_POSITION)
                    actionQueue.add(300)
                    {
                        Intake.setPower(0.7)
                        actionQueue.add(1300)
                        {
                            Shooter.setRPM(0.0)
                            Wicket.setPosition(Wicket.CLOSE_POSITION)
                        }
                    }
                }
        }
    )
    private val preCollectSeq = serial(
        execute{Joint.setPosition(Joint.COLLECT_POSITION)},
        execute{Intake.setPowerMain(1.0)} ,
        execute{Intake.setPowerSupport(0.8)},
    )
    private val afterCollectSeq = serial(
        execute{
            Intake.setPowerMain(0.4)
            Joint.setPosition(Joint.INIT_POSITION)
        }
    )

    override fun onInit(){
        super.onInit()
        //allianceColour=  Colours.RED//TODO change for each auto


        task = serial(
            execute{ goTo(90.0,90.0,45.0)},
            execute{Shooter.charge()},
            execute{Intake.setPowerMain(0.7)},
            sleepms(900),
            execute{Intake.setPowerMain(0.0)},
            sleepms(400),
            shootSeq,
            sleepms(700),
            execute{ Joint.setPosition(Joint.COLLECT_POSITION) },
            execute{ goTo(98.0,80.0,0.0)},
            preCollectSeq,
            sleepms(500),
            execute{ goTo(121.0,80.0,0.0)},
            sleepms(1000),
            afterCollectSeq,
            execute{Shooter.charge()},
            execute{Turret.setPosition(0.757)},
            execute{ goTo(88.0,79.0,0.0)},
            sleepms(850),
            shootSeq,
            sleepms(1200),
            execute{ goTo(90.0,56.25,0.0)},
            preCollectSeq,
            sleepms(500),
            execute{ goTo(120.0,56.25,0.0)},

            sleepms(700),
            afterCollectSeq,
            execute{Shooter.charge()},
            sleepms(700),
            //execute{ goTo(126.0,83.0,40.0)},//push the gate
            //sleepms(500),
            execute{ goTo(90.0,81.0,0.0)},
            sleepms(1000),
            shootSeq,
            sleepms(999999),
            sleepms(1200),
            execute{Joint.setPosition(Joint.INIT_POSITION)},
            execute{ goTo(128.0,64.2,0.0)},//push gate
            sleepms(800),//wait at gate
            execute{ goTo(125.5,50.2,60.0)},
            sleepms(700),
            preCollectSeq,
            sleepms(500),
            execute{ goTo(125.5,54.2,60.0)},
            sleepms(500),
            execute{Shooter.charge()},
            execute{ goTo(85.0,85.0,0.0)},
            sleepms(2500),
            shootSeq,

            sleepms(1300),
            execute{Joint.setPosition(Joint.INIT_POSITION)},
            execute{ goTo(128.0,64.2,0.0)},//push gate
            sleepms(1200),//wait at gate
            execute{ goTo(126.5,50.2,60.0)},
            sleepms(700),
            preCollectSeq,
            sleepms(500),
            execute{ goTo(126.5,54.2,60.0)},
            sleepms(500),
            execute{Shooter.charge()},
            execute{ goTo(85.0,85.0,0.0)},
            sleepms(2500),
            shootSeq,

            sleepms(1300),
            execute{Joint.setPosition(Joint.INIT_POSITION)},
            execute{ goTo(128.0,64.2,0.0)},//push gate
            sleepms(1200),//wait at gate
            execute{ goTo(126.5,50.2,60.0)},
            sleepms(700),
            preCollectSeq,
            sleepms(500),
            execute{ goTo(126.5,54.2,60.0)},
            sleepms(500),
            execute{Shooter.charge()},
            execute{ goTo(85.0,85.0,0.0)},
            sleepms(2500),
            shootSeq,





            )
    }

}