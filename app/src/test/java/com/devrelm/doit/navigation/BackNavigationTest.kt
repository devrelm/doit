package com.devrelm.doit.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class BackNavigationTest {
    @Test
    fun `back from Tasks returns Now`() {
        assertEquals(TopLevelDestination.NOW, previousDestinationOnBack(TopLevelDestination.TASKS))
    }

    @Test
    fun `back from Nudges returns Now`() {
        assertEquals(TopLevelDestination.NOW, previousDestinationOnBack(TopLevelDestination.NUDGES))
    }

    @Test
    fun `back from Now leaves the app`() {
        assertNull(previousDestinationOnBack(TopLevelDestination.NOW))
    }
}
