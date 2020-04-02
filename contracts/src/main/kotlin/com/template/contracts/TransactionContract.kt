package com.template.contracts

import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import com.template.states.TransactionRecordState
import java.security.PublicKey

class TransactionContract : Contract {
    companion object {
        @JvmStatic
        val transactionContractId = "com.template.contract.TransactionContract"
    }

    interface Commands : CommandData
    class AddTransaction : TypeOnlyCommandData(), Commands

    @Throws(IllegalArgumentException::class)
    override fun verify(tx: LedgerTransaction) {
        val rubberCommand = tx.commands.requireSingleCommand<Commands>()
        val setOfSigners = rubberCommand.signers.toSet()

        when (rubberCommand.value) {
            is AddTransaction -> verifyAddTransaction(tx, setOfSigners)
            else -> throw IllegalArgumentException("Unrecognised command.")
        }
    }

    private fun verifyAddTransaction (tx: LedgerTransaction , signers: Set<PublicKey>)  = requireThat {

        val out = tx.outputsOfType<TransactionRecordState>().single()
        "No input when create." using (tx.inputStates.isEmpty())
//            "Only one output state should be created." using (tx.outputStates.size == 1)
        "The source and the destination cannot be same entity." using (out.source != out.destination)
        "The rubber type must not empty." using (out.rubberType.isNotEmpty())
        "The volume must greater than 0." using (out.volume > 0)
        "The price must greater than 0." using (out.price > 0)

    }
}