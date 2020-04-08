package com.template.states

import com.template.contracts.TransactionContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.AnonymousParty
import java.util.*

@BelongsToContract(TransactionContract::class)
class TransactionState(val sender: AnonymousParty,
                            val rubberType: String,
                            val volume : Int,
                            val price : Int,
                            val recipient: AnonymousParty,
                            val invoiceID: UUID) : ContractState {
    override val participants: List<AbstractParty> get() = listOfNotNull(recipient,sender).map { it }
//    override val participants: List<AbstractParty> get() = listOf(destination,source).map{ it }
}