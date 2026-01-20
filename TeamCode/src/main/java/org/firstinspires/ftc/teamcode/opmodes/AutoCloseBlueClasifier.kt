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
class AutoCloseBlueClasifier : AutoBase(Pose(24.0, 123.0, Math.toRadians(138.0)),Colours.BLUE) {

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
            Shooter.setRPM(power)
            actionQueue.add(100) {
                Wicket.setPosition(Wicket.OPEN_POSITION)
                actionQueue.add(300) {
                    Shooter.setRPM(power)
                    Intake.setPowerMain(1.0)
                    Intake.setPowerSupport(0.7)
                    actionQueue.add(400) {
                        Shooter.setRPM(power)
                        actionQueue.add(700) {
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

    override fun onInit() {
        super.onInit()
        far=false

        task = serial(
            execute { goTo(56.0, 93.0, 135.0) }, // preload-1 (144-88)
            execute { Shooter.charge() },
            execute { Intake.setPowerMain(0.7) },
            sleepms(1000),
            shootSeq,
            sleepms(1100),
            execute { goTo(54.0, 58.3, 180.0) }, // pre collect -2
            preCollectSeq,
            sleepms(1000),
            execute { goTo(19.0, 58.3, 180.0) }, // collect
            sleepms(900),
            afterCollectSeq,
            execute { Turret.setPosition(0.185) },
            execute { Shooter.charge() },
            sleepms(300),
            execute { goTo(59.0, 72.0, 180.0) }, // shoot
            sleepms(1100),
            shootSeq,
            sleepms(900),
            execute { goTo(28.4, 60.3, 156.0) }, // collect -3
            sleepms(800),
            preCollectSeq,
            execute { goTo(11.5, 60.3, 156.0) }, // push gate

            sleepms(2200),
            execute { Shooter.charge() },
            execute { Turret.setPosition(0.21) },
            execute { goTo(59.0, 72.0, 180.0) },
            execute { Joint.setPosition(Joint.COLLECT_POSITION + 0.1) },
            sleepms(1800),
            shootSeq,


            sleepms(1100),
            execute { goTo(28.4, 60.3, 156.0) }, // collect -4
            sleepms(800),
            preCollectSeq,
            execute { goTo(11.4, 60.3, 156.0) }, // push gate
            sleepms(3000),
            execute { Shooter.charge() },
            execute { goTo(59.0, 79.0, 180.0) },
            sleepms(1000),
            execute { Joint.setPosition(Joint.COLLECT_POSITION + 0.1) },
            sleepms(800),
            shootSeq,

            sleepms(1100),
            execute { Joint.setPosition(Joint.COLLECT_POSITION) },
            execute { goTo(46.0, 80.6, 180.0) }, // pre collect -5
            preCollectSeq,
            sleepms(500),
            execute { goTo(25.0, 80.6, 180.0) }, // collect
            sleepms(750),
            afterCollectSeq,
            execute { Shooter.charge() },
            execute { goTo(59.0, 80.0, 180.0) },
            sleepms(950),
            shootSeq,


            sleepms(1100),
            execute { goTo(28.4, 60.3, 156.0)  }, // collect -6
            preCollectSeq,
            sleepms(1900),
            preCollectSeq,
            execute { goTo(11.4, 60.3, 156.0) }, // push gate
            sleepms(700),
            afterCollectSeq,
            execute { goTo(59.0, 79.0, 180.0) },
            sleepms(100),
            execute{Joint.setPosition(Joint.COLLECT_POSITION+0.1)},
            sleepms(1100),
            execute{Shooter.charge()},
            execute{Joint.setPosition(Joint.COLLECT_POSITION)},
            sleepms(400),
            shootSeq,

            sleepms(900),
            execute { goTo(59.0, 65.0, 180.0) },

            sleepms(999999999)
        )
    }
}