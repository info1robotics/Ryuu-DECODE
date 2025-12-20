package org.firstinspires.ftc.teamcode.common

import kotlin.math.atan
import kotlin.math.sqrt
import kotlin.math.tan

class Alfa {
    val G: Double = 9.81 //acceleratia gravitationala 9.81 m/s2
    val R0: Double = 0.15 //raza rulmentului lansatorului 15cm=0.15m
    val H0: Double = 0.20 //inaltimea initiala 20cm=0.2m
    val PI: Double = Math.PI
    val hvert = 30
    var alfa1: Double = 0.0
    var alfa2: Double = 0.0

    var final_alfa = 0.0


    fun calculate(doriz: Double, vrotatie: Double): Double {//distanta orizontala, inaltime verticala, viteza
        val v0 = vrotatie * R0 //viteza initiala= viteza de rotatie * R0
        val a = G * doriz * doriz / (2 * v0 * v0) //un grup de constante a=g*d^2/(2*v0^2)

        val delta =
            doriz * doriz - 4 * a * (hvert - H0 + a) //discriminantul ecuatiei in tangent alfa

        alfa1 = atan((doriz + sqrt(delta)) / (2 * a))
        alfa2 = atan((doriz - sqrt(delta)) / (2 * a))


        if(Math.abs(alfa1)>PI/2)
            final_alfa =  alfa2*180/PI
        else final_alfa = alfa1*180/PI


        return final_alfa
    }

    fun verify(doriz: Double, alfa: Double, vrotatie: Int): Double {
        val v0 = vrotatie * R0 //viteza initiala= viteza de rotatie * R0
        val a = G * doriz * doriz / (2 * v0 * v0) //un grup de constante a=g*d^2/(2*v0^2)
        val hvert = H0 + doriz * tan(alfa) - a * (tan(alfa) * tan(alfa) + 1)

        return hvert
    }
}