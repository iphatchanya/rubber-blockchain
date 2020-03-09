package com.template.flows

import net.corda.core.flows.*
import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.accounts.workflows.accountService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.utilities.getOrThrow


@StartableByRPC
@StartableByService
@InitiatingFlow
class CreateNewAccount(private val accountName:String) : FlowLogic<String>() {

    @Suspendable
    override fun call(): String {
        //Create a new account
        val newAccount = accountService.createAccount(name = accountName).toCompletableFuture().getOrThrow()
        val account = newAccount.state.data
        return ""+account.name + " team's account was created. UUID is : " + account.identifier
    }
}