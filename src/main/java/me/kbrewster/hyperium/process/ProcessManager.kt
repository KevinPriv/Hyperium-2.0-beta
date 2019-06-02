package me.kbrewster.hyperium.process

interface ProcessManager: Manager {

    val startupProcesses: MutableList<Process>

    val runningProcesses: MutableList<Process>

    override fun onInitialisation() {
        this.runningProcesses += startupProcesses
    }

    override fun onShutdown() {
        this.runningProcesses.forEach { process -> process.kill() }
    }

}