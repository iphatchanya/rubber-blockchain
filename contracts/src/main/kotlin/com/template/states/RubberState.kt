package com.template.states

import com.template.contracts.TransactionContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

@BelongsToContract(TransactionContract::class)
data class RubberState(val source : Party,
                       val rubberType : String,
                       val volume : Int,
                       val price : Int,
                       val destination : Party,
                       override val linearId: UniqueIdentifier = UniqueIdentifier()) : LinearState {


    override val participants: List<AbstractParty> get() = listOf(source, destination)
}
