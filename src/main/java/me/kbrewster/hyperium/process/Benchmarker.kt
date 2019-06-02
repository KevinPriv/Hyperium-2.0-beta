package me.kbrewster.hyperium.process

import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit


class Benchmarker {

    /**
     * Starts Benchmarker
     */
    val stopwatch: Stopwatch by lazy { Stopwatch.createStarted() }

    /**
     * How long it took to execute the last benchmark
     */
    var millis = 0L

    inline fun benchmark(task: () -> Unit) {
        stopwatch.run {
            task.invoke()
            this.reset() // optional
            millis = this.elapsed(TimeUnit.MILLISECONDS)
        }
    }
}