package me.kbrewster.hyperium.process

class ProcessListings(val startupProcesses: MutableList<Process>): ArrayList<Process>(), Manager {

    override fun onInitialisation() {
        this += startupProcesses
    }

    override fun onShutdown() {
        this.forEach { process -> process.kill() }
    }

}