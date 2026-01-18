package org.firstinspires.ftc.teamcode.opmodes
import com.pedropathing.follower.Follower
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.AprilTags
import org.firstinspires.ftc.teamcode.common.AutoConstants
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.enums.Colours
import org.firstinspires.ftc.teamcode.pedro.Constants
import org.firstinspires.ftc.teamcode.pinpoint.Pinpoint
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Hood
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Jack
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Shooter
import org.firstinspires.ftc.teamcode.subsystems.Turret
import org.firstinspires.ftc.teamcode.subsystems.Wicket
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepuntil
import kotlin.math.absoluteValue

@TeleOp
class Teleop : LinearOpMode() {

    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y

    private var startPose: Pose = AutoConstants.CENTER_POS
    private var allianceColour:Colours = Colours.RED

    lateinit var gamepadEx1: GamepadEx
    lateinit var gamepadEx2: GamepadEx
    lateinit var follower: Follower
    val actionQueue = ActionQueue()

    private var empty = 1.0//for intake
    private var transition = false//ignore other systems commands while in a motion
    private var distanceLL = 0.0//distance got from limelight
    private var distancePP = 0.0//distance got from odo
    private var distance = 0.0
    private var max = 250

    var forwardPower =0.0
    var strafePower = 0.0
    var primaryRotationPower = 0.0
    var secondaryRotationPower = 0.0

    private fun handleInputDrivetrain()
    {
        forwardPower = gamepad1.corrected_left_stick_y().toDouble()
        strafePower =   gamepad1.left_stick_x.toDouble()
        primaryRotationPower = (gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble())

        secondaryRotationPower = if(gamepad1.right_bumper)
            0.2
        else if(gamepad1.left_bumper)
            -0.2
        else
            0.0

        if(primaryRotationPower==0.0)
            Drivetrain.driveMecanum(forwardPower, strafePower, secondaryRotationPower, 1.0)
        else
            Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
    }
    private fun handleInputIntake()
    {
        if(!transition)
        {

            if(gamepad2.right_trigger+gamepad2.left_trigger>0)
                Joint.setPosition(Joint.COLLECT_POSITION)
            else
                Joint.setPosition(Joint.INIT_POSITION)

            Intake.setPowerSupport((gamepad2.right_trigger.toDouble())*(0.6))
            Intake.setPowerMain(gamepad2.right_trigger.toDouble())
            if (gamepad2.left_trigger > 0.1 )
            {
                Intake.reverse()
                empty = 1.0
            }

        }
    }
    private fun handleInputJack()
    {
        if(gamepad2.dpad_up) Jack.setPosition(Jack.LOWER_LIMIT)
        if(gamepad2.dpad_down) Jack.setPosition(Jack.HIGHER_LIMIT)
    }
    var far = false
    var power = 0.0
    private fun handleInputShooter() {

        if(distance<max)
        {
            far = false
            Hood.setPosition(Hood.calculate(distance))
            if(gamepadEx2.getButtonDown("a"))
            {
                Wicket.setPosition(Wicket.OPEN_POSITION)
                transition=true
                actionQueue.add(1200)//if this doesn t work 1200
                    {
                        Intake.setPowerMain(1.0)
                        Intake.setPowerSupport(1.0)
                        actionQueue.add(900)
                        {
                            Intake.stop()
                            Shooter.setRPM(0.0)
                            Wicket.setPosition(Wicket.CLOSE_POSITION)
                            transition = false
                            empty = 1.0
                        }
                    }
            }
        }
        else
        {
            if(gamepadEx2.getButtonDown("a")) {
                Intake.stop()
                Shooter.setRPM(2900.0)
                Hood.setPosition(0.68)
                actionQueue.add(100) {
                    Wicket.setPosition(Wicket.OPEN_POSITION)
                    actionQueue.add(300) {
                        Shooter.setRPM(2900.0)
                        Intake.setPowerMain(1.0)
                        Intake.setPowerSupport(0.65)
                        actionQueue.add(400) {
                            Shooter.setRPM(2900.0)
                            actionQueue.add(1300) {
                                Shooter.setRPM(0.0)
                                Wicket.setPosition(Wicket.CLOSE_POSITION)
                            }
                        }
                    }
                }
            }
        }

        if(transition)
            Shooter.setRPM(power)

    }
    var tx =0.0
    private fun handleInputTurret() {
        Turret.setPosition(0.5)
        //Turret.lock(tx)
    }
    override fun runOpMode() {
        Controller.init(hardwareMap)
        Pinpoint.init(hardwareMap)

        Limelight.start()

        val log = Log(telemetry)

        follower = Constants.createFollower(hardwareMap);
        follower.pose = startPose


        empty=1.0
        log.tick()
        waitForStart()

        while (gamepad1.atRest());
        Controller.setInit()
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)

        while (opModeIsActive() && !isStopRequested) {

            handleInputDrivetrain()
            handleInputIntake()
            handleInputShooter()
            handleInputTurret()
            handleInputJack()

            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()
            follower.update()

            if(gamepad1.dpad_right) {
                allianceColour = Colours.RED
                Limelight.allianceTag = AprilTags.RED
                follower = Constants.createFollower(hardwareMap)
                follower.pose = AutoConstants.RED_TELE_POS
            }
            else if(gamepad1.dpad_left) {
                allianceColour = Colours.BLUE
                Limelight.allianceTag = AprilTags.BLUE
                follower = Constants.createFollower(hardwareMap)
                follower.pose = AutoConstants.BLUE_TELE_POS
            }

            power = Shooter.calculate(distance)
            var ta = Limelight.getTa()
            var distanceLL = ta?.let { Limelight.getDistanceToAprilTag(it) }
            distancePP = Pinpoint.distance(follower.pose.x,follower.pose.y, allianceColour)
            distance = distancePP//change distance method

            log.add("choose alliance colour RED/BLUE by dpad left/right",allianceColour.toString())
            Limelight.getTx()?.let { log.add("tx", it) }
            Limelight.getTa()?.let  { log.add("ta", it) }
            //tx= Limelight.getTx()!!
            log.add("distanceLL $distanceLL")
            log.add("distancePP $distancePP")
            //log.add("is empty",Intake.isEmpty())
            //log.add("rgb",Intake.getColorReading())
            log.add("@X", follower.pose.x)
            log.add("@Y", follower.pose.y)
            log.add("@Heading", Math.toDegrees(follower.pose.heading))
            log.add("distance from $allianceColour goal: $distancePP")
            log.add(("far "+(far).toString()))
            log.tick()
        }
    }
}