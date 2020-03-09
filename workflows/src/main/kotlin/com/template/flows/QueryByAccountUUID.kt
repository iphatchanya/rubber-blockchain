package com.template.flows

import net.corda.core.flows.*
import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.accounts.workflows.accountService
import com.template.states.InternalMessageState
import com.template.states.TemplateState
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.node.services.vault.QueryCriteria


@StartableByRPC
@StartableByService
@InitiatingFlow
class ViewInboxByAccount(
        val acctname : String
) : FlowLogic<List<String>>() {

    @Suspendable
    override fun call(): List<String> {

        val myAccount = accountService.accountInfo(acctname).single().state.data
        val criteria = QueryCriteria.VaultQueryCriteria(
                externalIds = listOf(myAccount.identifier.id)
        )
        val InternalMessages = serviceHub.vaultService.queryBy(
                contractStateType = InternalMessageState::class.java,
                criteria = criteria
        ).states.map { "\n" + "Internal Message: " + it.state.data.task }

        val invoices = serviceHub.vaultService.queryBy(
                contractStateType = TemplateState::class.java,
                criteria = criteria
        ).states.map { "\n" +"Invoice State : " +it.state.data}


        return invoices
    }
}
