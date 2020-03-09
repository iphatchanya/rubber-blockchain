package com.template.states


import com.template.contracts.InternalMessageContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.AnonymousParty


@BelongsToContract(InternalMessageContract::class)
class InternalMessageState(
        val task: String,
        val from: AnonymousParty,
        val to: AnonymousParty) : ContractState {
    override val participants: List<AbstractParty> get() = listOfNotNull(from, to).map { it }
}
