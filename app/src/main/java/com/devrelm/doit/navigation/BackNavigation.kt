package com.devrelm.doit.navigation

/**
 * Where system back should take [current]. `null` means back should leave the app.
 */
fun previousDestinationOnBack(current: TopLevelDestination): TopLevelDestination? =
    when (current) {
        TopLevelDestination.NOW -> null
        TopLevelDestination.TASKS, TopLevelDestination.NUDGES -> TopLevelDestination.NOW
    }
