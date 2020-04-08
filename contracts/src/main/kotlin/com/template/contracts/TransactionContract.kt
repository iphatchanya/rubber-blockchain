package com.template.contracts

import com.template.states.TransactionState
import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction

class TransactionContract : Contract {
    companion object {
        const val ID = "com.template.contracts.TemplateContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands.Create>()

        requireThat {

        }
        when (command.value) {
            is Commands.Create -> requireThat {
                "No input when create." using (tx.inputStates.isEmpty())
                "Only one output state should be created." using (tx.outputStates.size == 1)
                val out: TransactionState = tx.outputStates.first() as TransactionState
                "The source and the destination cannot be same entity." using (out.sender != out.recipient)
//                "All of the participants must be signers." using (signers.containsAll(out.participants.map { it.owningKey }))
                "The rubber type must not empty." using (out.rubberType.isNotEmpty())
                "The volume must greater than 0." using (out.volume > 0)
                "The price must greater than 0." using (out.price > 0)
            }
        }
    }

    interface Commands : CommandData {
        class Create : Commands
    }
}