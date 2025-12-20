package org.firstinspires.ftc.teamcode.common

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import org.firstinspires.ftc.robotcore.external.Telemetry

class Log(telemetry: Telemetry?) {
    var telemetry: Telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)

    init {
        instance = this
    }

    fun add(name: String?, line: Any): Log {
        telemetry.addData(name, line.toString())
        return this
    }

    fun add(line: String?): Log {
        telemetry.addLine(line)
        return this
    }

    fun tick() {
        telemetry.update()
    }

    fun clear(): Log {
        telemetry.clearAll()
        return this
    }

    companion object {
        lateinit var instance: Log
    }
}
