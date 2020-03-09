package com.template.contracts

import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction

class InternalMessageContract : Contract {
    companion object {
        const val ID = "com.template.contract.InternalMessageContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands.Create>()

        requireThat {

        }
    }

    interface Commands : CommandData {
        class Create : Commands
    }
}