package me.kbrewster.hyperium.process

object TaskManager: ProcessManager {

    override val startupProcesses = mutableListOf<Process>()

    override val runningProcesses = mutableListOf<Process>()

    override fun onInitialisation() {
        super.onInitialisation()
    }

    override fun onShutdown() {
        super.onShutdown()
    }
}