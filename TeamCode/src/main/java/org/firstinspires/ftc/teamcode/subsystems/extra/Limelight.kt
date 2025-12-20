package org.firstinspires.ftc.teamcode.subsystems.extra

import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.robotcore.hardware.HardwareMap
import android.util.Log
import org.firstinspires.ftc.teamcode.common.AprilTags
import kotlin.math.abs
import kotlin.math.sqrt

object Limelight {
    private lateinit var limelight: Limelight3A

    val allianceTag = AprilTags.BLUE // TODO: change if needed

    fun init(hardwareMap: HardwareMap, pipelineIndex: Int = 0) {
        limelight = hardwareMap.get(Limelight3A::class.java, "limelight")
        limelight.pipelineSwitch(pipelineIndex)
    }

    fun start() = limelight.start()
    fun stop() = limelight.stop()

    fun saveSnapshot() {
        limelight.captureSnapshot("cache-${System.currentTimeMillis()}")
    }

    fun changePipeline(newIndex: Int) {
        try {
            limelight.pipelineSwitch(newIndex)
            Log.i("Limelight", "Switched to pipeline $newIndex")
        } catch (e: Exception) {
            Log.e("Limelight", "Failed to switch pipeline: ${e.message}")
        }
    }

    fun getAprilTagId(): Int? {
        val result = limelight.latestResult
        if (result == null || !result.isValid) return null

        val fiducials = result.fiducialResults
        return if (fiducials.isNotEmpty()) fiducials[0].fiducialId else null
    }


    fun getTx(): Double? {
        val result = limelight.latestResult ?: return null
        return if (result.isValid) result.tx else null
    }

    fun getTy(): Double? {
        val result = limelight.latestResult ?: return null
        return if (result.isValid) result.ty else null
    }

    fun isCentered(toleranceDeg: Double = 2.0): Boolean {
        val tx = getTx() ?: return false
        return abs(tx) < toleranceDeg
    }

    fun isPPG() = getAprilTagId() == AprilTags.PPG
    fun isPGP() = getAprilTagId() == AprilTags.PGP
    fun isGPP() = getAprilTagId() == AprilTags.PPG
    fun isAlliance() = getAprilTagId() == allianceTag

    fun getDistanceToAprilTag(
        tagHeightM: Double,      // height of the tag center from the ground in meters
        cameraHeightM: Double,   // height of the camera from the ground in meters
        cameraPitchDeg: Double = 0.0  // camera tilt in degrees (positive = pointing down)
    ): Double? {
        val tyDeg = Limelight.getTy() ?: return null

        val angleTotalRad = Math.toRadians(cameraPitchDeg + tyDeg)
        if (angleTotalRad == 0.0) return null

        val distance = (tagHeightM - cameraHeightM) / kotlin.math.tan(angleTotalRad)
        return if (distance.isFinite()) kotlin.math.abs(distance) else null
    }
}