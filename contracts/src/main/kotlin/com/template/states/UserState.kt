package com.template.states

import com.template.contracts.RubberContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party


@BelongsToContract(RubberContract::class)
data class UserState (val source : Party,
                      val name : String,
                      val certification : String,
                      val destination : Party,
                      override val linearId: UniqueIdentifier = UniqueIdentifier()) : LinearState {


    override val participants: List<AbstractParty> get() = listOf(source, destination)
}
