package com.devrelm.doit.tasks

/**
 * How long a task is expected to take, in minutes. A `null` [Estimate] means "not sure".
 */
@JvmInline
value class Estimate(val minutes: Int) {
    init {
        require(minutes > 0)
    }
}

/** The size bucket a task falls into based on its [Estimate]. */
enum class TaskSize {
    QUICK,
    MEDIUM,
    LONG,
}

private const val MAX_MINUTES_QUICK = 15
private const val MAX_MINUTES_MEDIUM = 60

val Estimate.size: TaskSize
    get() = when {
        minutes <= MAX_MINUTES_QUICK -> TaskSize.QUICK
        minutes <= MAX_MINUTES_MEDIUM -> TaskSize.MEDIUM
        else -> TaskSize.LONG
    }
