package com.template.states

import com.template.contracts.TransactionContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.AnonymousParty
import java.util.*

@BelongsToContract(TransactionContract::class)
data class TransactionState(val invoiceID: UUID,
                            val source: AnonymousParty,
                            val rubberType: String,
                            val volume : Int,
                            val price : Int,
                            val destination: AnonymousParty) : ContractState {
    override val participants: List<AbstractParty> get() = listOfNotNull(destination,source).map { it }
//    override val participants: List<AbstractParty> get() = listOf(destination,source).map{ it }
}