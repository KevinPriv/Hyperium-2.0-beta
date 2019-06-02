package me.kbrewster.hyperium.process

import me.kbrewster.hyperium.bin.hud.HUD

object TaskManager : Manager {

    val runningProcesses = ProcessListings(startupProcesses = mutableListOf(HUD))

    override fun onInitialisation() {
        this.runningProcesses.onInitialisation()
    }

    override fun onShutdown() {
        this.runningProcesses.onShutdown()
    }
}