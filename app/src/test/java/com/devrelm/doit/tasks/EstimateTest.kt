package com.devrelm.doit.tasks

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EstimateTest {
    @Test
    fun `1 minute is Quick`() {
        assertEquals(TaskSize.QUICK, Estimate(1).size)
    }

    @Test
    fun `15 minutes is Quick`() {
        assertEquals(TaskSize.QUICK, Estimate(15).size)
    }

    @Test
    fun `16 minutes is Medium`() {
        assertEquals(TaskSize.MEDIUM, Estimate(16).size)
    }

    @Test
    fun `60 minutes is Medium`() {
        assertEquals(TaskSize.MEDIUM, Estimate(60).size)
    }

    @Test
    fun `61 minutes is Long`() {
        assertEquals(TaskSize.LONG, Estimate(61).size)
    }

    @Test
    fun `240 minutes is Long`() {
        assertEquals(TaskSize.LONG, Estimate(240).size)
    }

    @Test
    fun `zero minutes throws`() {
        assertThrows(IllegalArgumentException::class.java) { Estimate(0) }
    }

    @Test
    fun `negative minutes throws`() {
        assertThrows(IllegalArgumentException::class.java) { Estimate(-5) }
    }
}
