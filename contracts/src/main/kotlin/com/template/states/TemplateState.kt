package com.template.states

import com.template.contract.TemplateContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.AnonymousParty
import java.util.*


@BelongsToContract(TemplateContract::class)
data class TemplateState(val invoiceID: UUID,
                         val source: AnonymousParty,
                         val rubberType: String,
                         val volume : Int,
                         val price : Int,
                         val destination: AnonymousParty) : ContractState {
    override val participants: List<AbstractParty> get() = listOfNotNull(destination,source).map { it }
}