package me.kbrewster.hyperium.process

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Basic Process Interface
 *
 * We can think of a process as a task on
 * the windows command manager, we can kill services
 * and if a service gets killed, all the child processes
 * get killed along with it.
 */
abstract class Process : CoroutineScope {

    val childProcesses: MutableList<Process> = mutableListOf()

    var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    /**
     * This function is called at the end of this processes lifecycle.
     * This is where you should close anything being used by this process,
     * and prepare for shutdown.
     *
     * If this function returns true, then the service has been destroyed correctly,
     * and will be removed. If it returns false, then the process has failed to kill,
     * and will not be removed.
     *
     * Keep in mind that this is not necessarily the end of the game.
     * Services should be able to shutdown and reboot throughout
     * the duration of the game without any issues.
     */
    fun kill(): Boolean {
        this.childProcesses.forEach { service -> service.kill() }
        this.job.cancel()
        return true
    }
}