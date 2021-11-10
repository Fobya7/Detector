package com.s452635.detector

import java.lang.Thread.sleep

class Generator(
    startingSpeed: Int, startingState: RotationState
)
{
    // constant
    val measureTik = 200
    val changeTik = 10
    val toothLength = 20

    // measures
    val toothState: ToothState? = null
    val currentToothDist: Int? = null
    val measureLength = 2000

    // changeable
    private var speed: Int? = null
    private var rotationState: RotationState? = null

    init
    {
        speed = startingSpeed
        rotationState = startingState
    }

    fun change()
    {

    }

    fun measure()
    {

    }

    fun generate()
    {
        val thread1 = Thread {
            println( "1 > started" )
            sleep( 40 )
            println( "1 > finished" )
        }
        val thread2 = Thread {
            println( "2 > started" )
            sleep( 30 )
            println( "2 > finished" )
        }
        thread1.start()
        thread2.start()
    }
}

enum class RotationState
{
    Left,
    Right,
    Stop
}

enum class ToothState
{
    Tooth,
    Groove
}

fun main()
{
    val gen = Generator( 20, RotationState.Stop )
    gen.generate()
}