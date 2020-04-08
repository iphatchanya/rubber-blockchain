package com.template.flows

import net.corda.core.flows.*
import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.accounts.workflows.accountService
import com.r3.corda.lib.accounts.workflows.flows.RequestKeyForAccount
import com.template.states.TransactionState
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.node.services.vault.QueryCriteria


@StartableByRPC
@StartableByService
@InitiatingFlow
class ViewInboxByAccount(
        val accountName : String
) : FlowLogic<List<String>>() {

    @Suspendable
    override fun call(): List<String> {

        val myAccount = accountService.accountInfo(accountName).single().state.data
        val criteria = QueryCriteria.VaultQueryCriteria(
                externalIds = listOf(myAccount.identifier.id)
        )
        val invoices = serviceHub.vaultService.queryBy(
                contractStateType = TransactionState::class.java,
                criteria = criteria
        ).states.map {
            "\n" + "State data = " + it.state.data.participants +
            "\n" + "SubFlowSender = " + accountService.accountInfo(it.state.data.sender.toString()).single().state.data +
            "\n" + "SubFlowRecipient = " + accountService.accountInfo(it.state.data.recipient.toString()).single().state.data +
            "\n" +" Invoice State : Invoice ID = " + it.state.data.invoiceID +
                ", Source = " + it.state.data.sender + ", Rubber type = " + it.state.data.rubberType +
                ", Volume = " + it.state.data.volume + ", Price = " + it.state.data.price +
                ", Destination = " + it.state.data.recipient.toString()
//            "\n" + "Invoice State : " + it.state.data


        }

        return invoices
    }
}
