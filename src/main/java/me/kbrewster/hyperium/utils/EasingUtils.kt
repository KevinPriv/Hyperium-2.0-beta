package me.kbrewster.hyperium.utils

object EasingUtils {
    fun easeOut(current: Float, goal: Float, jump: Float, speed: Float) =
            if (Math.floor((Math.abs(goal - current) / jump).toDouble()) > 0)
                current + (goal - current) / speed
            else goal
}