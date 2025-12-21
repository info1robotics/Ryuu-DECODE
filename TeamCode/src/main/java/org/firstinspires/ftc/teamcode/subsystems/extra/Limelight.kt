package org.firstinspires.ftc.teamcode.subsystems.extra

import android.R.attr.x
import android.R.attr.y
import android.util.Log
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.AprilTags
import kotlin.math.abs
import kotlin.math.pow


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
    fun getTa():Double?{
        val result = limelight.latestResult ?: return null
        return if(result.isValid) result.ta else null
    }

    fun isCentered(toleranceDeg: Double = 2.0): Boolean {
        val tx = getTx() ?: return false
        return abs(tx) < toleranceDeg
    }

    fun isPPG() = getAprilTagId() == AprilTags.PPG
    fun isPGP() = getAprilTagId() == AprilTags.PGP
    fun isGPP() = getAprilTagId() == AprilTags.PPG
    fun isAlliance() = getAprilTagId() == allianceTag

    fun getDistanceToAprilTag(ta: Double): Double {
        return 188.8901 * ta.pow(-0.607961)
    }
}