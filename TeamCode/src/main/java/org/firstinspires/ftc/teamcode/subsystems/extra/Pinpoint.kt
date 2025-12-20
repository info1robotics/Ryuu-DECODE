package org.firstinspires.ftc.teamcode.pinpoint

//import com.pedropathing.localization.Pose
import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import kotlin.math.pow
import kotlin.math.round

object Pinpoint {
    fun Double.roundTo(decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return round(this * factor) / factor
    }

    //lateinit var pinpoint: com.pedropathing.localization.GoBildaPinpointDriver

    @JvmStatic
    fun init(hardwareMap: HardwareMap) {
        /*
        pinpoint = hardwareMap.get(com.pedropathing.localization.GoBildaPinpointDriver::class.java, "pinpoint")
        pinpoint.setOffsets(89.5, 151.5)
        pinpoint.setEncoderResolution(com.pedropathing.localization.GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
        pinpoint.setEncoderDirections(com.pedropathing.localization.GoBildaPinpointDriver.EncoderDirection.REVERSED, com.pedropathing.localization.GoBildaPinpointDriver.EncoderDirection.REVERSED);
        pinpoint.resetPosAndIMU()

         */
    }

    @JvmStatic
    fun reset() {
        //pinpoint.resetPosAndIMU()
    }

    @JvmStatic
    fun update() {
        //pinpoint.update()
    }

    @JvmStatic
    fun getPosition() {
        //return pinpoint.position
    }

}