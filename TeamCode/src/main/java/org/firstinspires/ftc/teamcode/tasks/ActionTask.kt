package org.firstinspires.ftc.teamcode.tasks

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.canvas.Canvas
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action

class ActionTask(var action: Action) : Task() {
    var canvas: Canvas? = null
    var b: Boolean = true

    override fun run() {
        super.run()
        canvas = Canvas()
        action.preview(canvas!!)
    }

    override fun tick() {
        super.tick()

        if (!b) {
            state = State.FINISHED
            return
        }

        val packet = TelemetryPacket()
        packet.fieldOverlay().operations.addAll(canvas!!.operations)

        b = action.run(packet)

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }
}
