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
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
@TeleOp
class Teleop : LinearOpMode() {

    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y

    private var startPose: Pose = AutoConstants.RED_TELE_POS
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
    private var heading = 0.0//robot s heading
    private var max = 200

    private fun handleInputDrivetrain()
    {
        val forwardPower = gamepad1.corrected_left_stick_y().toDouble()
        val strafePower =   gamepad1.left_stick_x.toDouble()
        val primaryRotationPower = (gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble())*0.6
        Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
    }
    private fun handleInputIntake()
    {
        /*
        if(!Intake.isEmpty())
            empty=0.0

         */
        if(gamepadEx2.getButtonDown("x") && empty==1.0)
            empty=0.0
        else if(gamepadEx2.getButtonDown("x") && empty==0.0)
            empty=1.0

        if(!transition)
        {
            if(gamepad2.right_trigger+gamepad2.left_trigger>0)
                Joint.setPosition(Joint.COLLECT_POSITION)
            else
                Joint.setPosition(Joint.INIT_POSITION)

            Intake.setPowerSupport((gamepad2.right_trigger.toDouble())*(0.3)*empty)
            Intake.setPowerMain(gamepad2.right_trigger.toDouble()*1.0)
            if (gamepad2.left_trigger > 0.1 ) {
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
    private fun handleInputShooter() {

        if(distance<max)
        {
            far = false
            var power = Shooter.calculate(distance)
            Hood.setPosition(Hood.calculate(distance))
            if(gamepadEx2.getButtonDown("a"))
            {
                transition=true
                Shooter.setRPM(power)
                actionQueue.add(1000)
                {
                    Intake.setPower(0.7)
                    actionQueue.add(600)
                    {
                        Intake.stop()
                        Shooter.stop()
                        transition = false
                        empty = 1.0
                    }
                }
            }
        }
        if(distancePP>max)
        {
            far=true
            Hood.setPosition(Hood.HIGHER_LIMIT)
            if(gamepadEx2.getButtonDown("a"))
            {
                transition=true
                Shooter.setRPM(4800.0)
                actionQueue.add(2100)
                {
                    Intake.setPower(1.0)
                    actionQueue.add(100)
                    {
                        Shooter.setRPM(5100.0)
                        actionQueue.add(600)
                        {
                            Intake.stop()
                            Shooter.stop()
                            transition = false
                            empty = 1.0
                        }
                    }


                }
            }
        }

    }
    private fun handleInputTurret() {
        Turret.lock()
    }
    override fun runOpMode() {
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)

        Controller.init(hardwareMap)
        Pinpoint.init(hardwareMap)

        Limelight.start()

        val log = Log(telemetry)

        follower = Constants.createFollower(hardwareMap);
        follower.pose = startPose

        Controller.setInit()
        empty=1.0
        if(gamepad1.dpad_up) {
            allianceColour = Colours.RED
            startPose=AutoConstants.RED_TELE_POS
            Limelight.allianceTag = AprilTags.RED
        }
        else if(gamepad1.dpad_down) {
            allianceColour = Colours.BLUE
            startPose = AutoConstants.BLUE_TELE_POS
            Limelight.allianceTag = AprilTags.BLUE
        }
        log.add("choose alliance colour RED/BLUE by dpad up/down",allianceColour.toString())

        log.tick()
        waitForStart()

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
            heading = follower.pose.heading

            var ta = Limelight.getTa()
            var distanceLL = ta?.let { Limelight.getDistanceToAprilTag(it) }
            distancePP = Pinpoint.distance(follower.pose.x,follower.pose.y, allianceColour)
            distance = distancePP//change distance method


            Limelight.getTx()?.let { log.add("tx", it) }
            Limelight.getTa()?.let  { log.add("ta", it) }

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