package me.kbrewster.hyperium.process

object TaskManager: Manager {

    val runningProcesses = ProcessListings(startupProcesses = mutableListOf<Process>())

    override fun onInitialisation() {
        this.runningProcesses.onInitialisation()
    }

    override fun onShutdown() {
        this.runningProcesses.onShutdown()
    }
}