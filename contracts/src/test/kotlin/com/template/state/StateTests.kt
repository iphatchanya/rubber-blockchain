package com.template.test

import com.template.states.TransactionState
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AnonymousParty
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class StateTests {

    @Test
    fun `State get contain correct field and type`() {
        assertEquals(TransactionState::class.java.getDeclaredField("invoiceID").type, UUID::class.java, "Field 'linearId' is not UUID.")
        assertEquals(TransactionState::class.java.getDeclaredField("source").type, AnonymousParty::class.java, "Field 'source' is not a AnonymousParty.")
        assertEquals(TransactionState::class.java.getDeclaredField("rubberType").type, String::class.java, "Field 'rubberType' is not a String.")
        assertEquals(TransactionState::class.java.getDeclaredField("volume").type, Int::class.java, "Field 'volume' is not a Int.")
        assertEquals(TransactionState::class.java.getDeclaredField("price").type, Int::class.java, "Field 'price' is not a Int.")
        assertEquals(TransactionState::class.java.getDeclaredField("destination").type, AnonymousParty::class.java, "Field 'destination' is not a AnonymousParty.")
    }

    @Test
    fun `State is Contract State` () {
        assert(ContractState::class.java.isAssignableFrom(TransactionState::class.java))
    }
}