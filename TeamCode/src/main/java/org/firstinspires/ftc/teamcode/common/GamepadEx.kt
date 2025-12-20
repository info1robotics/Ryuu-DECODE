package org.firstinspires.ftc.teamcode.common

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.enums.Direction
import org.firstinspires.ftc.teamcode.enums.Stick
import kotlin.math.abs

class GamepadEx(private val gamepad: Gamepad) {
    /*
	Digital input:
	0	START
	1	BACK
	2	MODE
	3	A
	4	B
	5	X
	6	Y
	7	DPAD_UP
	8	DPAD_RIGHT
	9	DPAD_DOWN
	10	DPAD_LEFT
	11	BUMPER_LEFT
	12	BUMPER_RIGHT
	13	JOYSTICK_LEFT
	14	JOYSTICK_RIGHT
	Analog input:
	0	JOYSTICK_LEFT_X
	1	JOYSTICK_LEFT_Y
	2	JOYSTICK_RIGHT_X
	3	JOYSTICK_RIGHT_Y
	4	TRIGGER_LEFT
	5	TRIGGER_RIGHT
	*/
    private var buttonState = BooleanArray(16)
    private val buttonDown = BooleanArray(16)
    private val buttonUp = BooleanArray(16)

    private val analog = FloatArray(6)

    init {
        update()
    }

    fun update() {
        analog[0] = gamepad.left_stick_x
        analog[1] = gamepad.left_stick_y
        analog[2] = gamepad.right_stick_x
        analog[3] = gamepad.right_stick_y
        analog[4] = gamepad.left_trigger
        analog[5] = gamepad.right_trigger

        val newButtonState = BooleanArray(16)

        newButtonState[0] = gamepad.start
        newButtonState[1] = gamepad.back
        newButtonState[2] = false
        newButtonState[3] = gamepad.a and !newButtonState[0] // ignore start + a
        newButtonState[4] = gamepad.b and !newButtonState[0] // ignore start + b
        newButtonState[5] = gamepad.x
        newButtonState[6] = gamepad.y
        newButtonState[7] = gamepad.dpad_up
        newButtonState[8] = gamepad.dpad_right
        newButtonState[9] = gamepad.dpad_down
        newButtonState[10] = gamepad.dpad_left
        newButtonState[11] = gamepad.left_bumper
        newButtonState[12] = gamepad.right_bumper
        newButtonState[13] = gamepad.left_stick_button
        newButtonState[14] = gamepad.right_stick_button
        newButtonState[15] = gamepad.touchpad

        for (i in 0..15) {
            buttonDown[i] = newButtonState[i] && (newButtonState[i] != buttonState[i])
            buttonUp[i] = (!newButtonState[i]) && (newButtonState[i] != buttonState[i])
        }
        buttonState = newButtonState
    }

    fun getAnalog(analog: String): Float {
        return this.analog[analogName[analog]!!]
    }

    fun getButton(button: String): Boolean {
        return buttonState[buttonName[button]!!]
    }

    fun getButtonDown(button: String): Boolean {
        return buttonDown[buttonName[button]!!]
    }

    fun getButtonUp(button: String): Boolean {
        return buttonUp[buttonName[button]!!]
    }

    companion object {
        val buttonName: HashMap<String?, Int?> = object : HashMap<String?, Int?>() {
            init {
                put("start", 0)
                put("back", 1)
                put("mode", 2)
                put("a", 3)
                put("b", 4)
                put("x", 5)
                put("y", 6)
                put("dpad_up", 7)
                put("dpad_right", 8)
                put("dpad_down", 9)
                put("dpad_left", 10)
                put("bumper_left", 11)
                put("bumper_right", 12)
                put("joystick_left", 13)
                put("joystick_right", 14)
                put("touchpad", 15)
            }
        }

        val analogName: HashMap<String?, Int?> = object : HashMap<String?, Int?>() {
            init {
                put("left_x", 0)
                put("left_y", 1)
                put("right_x", 2)
                put("right_y", 3)
                put("left_trigger", 4)
                put("right_trigger", 5)
            }
        }

        fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y
        fun Gamepad.corrected_right_stick_y(): Float = -this.right_stick_y

        fun Gamepad.getStickDirection(stick: Stick): Direction {
            val gamepadSticks = if (stick == Stick.LEFT) this.left_stick_x.toDouble() to this.corrected_left_stick_y().toDouble()
            else this.right_stick_x.toDouble() to this.corrected_right_stick_y().toDouble()

            val maxDirection = listOf(
                gamepadSticks.first,
                gamepadSticks.second,
                Math.abs(gamepadSticks.first),
                Math.abs(gamepadSticks.second)
            ).maxOrNull() ?: 0.0

            if (maxDirection < 0.4) return Direction.NONE

            val direction = when (maxDirection) {
                gamepadSticks.first -> Direction.RIGHT
                gamepadSticks.second -> Direction.UP
                abs(gamepadSticks.first) -> Direction.LEFT
                abs(gamepadSticks.second) -> Direction.DOWN
                else -> Direction.NONE
            }
            return direction
        }
    }
}