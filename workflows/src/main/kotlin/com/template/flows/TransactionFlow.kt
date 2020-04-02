package com.template.flows

import net.corda.core.flows.*
import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.lib.accounts.contracts.states.AccountInfo
import com.r3.corda.lib.accounts.workflows.accountService
import com.r3.corda.lib.accounts.workflows.flows.RequestKeyForAccount
import com.template.contract.TemplateContract
import com.template.states.TemplateState
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.StartableByRPC
import net.corda.core.identity.AnonymousParty
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import net.corda.core.node.StatesToRecord
import net.corda.core.utilities.ProgressTracker


object TransactionFlow {
    @StartableByRPC
    @StartableByService
    @InitiatingFlow
    class AddTransaction (val source: String,
                          val rubberType: String,
                          val volume: Int,
                          val price: Int,
                          val destination: String) : FlowLogic<String>() {
        companion object {
            object GENERATING_KEYS : ProgressTracker.Step("Generating Keys for transactions.")
            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction for between accounts")
            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contract constraints.")
            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with our private key.")
            object GATHERING_SIGS : ProgressTracker.Step("Gathering the counterparty's signature.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    GENERATING_KEYS,
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGS,
                    FINALISING_TRANSACTION
            )
        }

        override val progressTracker = tracker()
        @Suspendable
        override fun call(): String {
            val notaty = serviceHub.networkMapCache.notaryIdentities.first()
            //Generate key for transaction (create from destination to source)
            progressTracker.currentStep = GENERATING_KEYS
            val myAccount = accountService.accountInfo(destination).single().state.data
            val myKey = subFlow(NewKeyForAccount(myAccount.identifier.id)).owningKey
            val targetAccount = accountService.accountInfo(source).single().state.data
            val targetAcctAnonymousParty = subFlow(RequestKeyForAccount(targetAccount))

            //generating State for transfer
            progressTracker.currentStep = GENERATING_TRANSACTION
            val output = TemplateState(UUID.randomUUID(), AnonymousParty(myKey), rubberType, volume, price, targetAcctAnonymousParty)
            val transactionBuilder = TransactionBuilder(notaty)
            transactionBuilder.addOutputState(output)
                    .addCommand(TemplateContract.Commands.Create(), listOf(targetAcctAnonymousParty.owningKey, myKey))

            progressTracker.currentStep = VERIFYING_TRANSACTION
            // Verify that the transaction is valid.
            transactionBuilder.verify(serviceHub)

            //Pass along Transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val locallySignedTx = serviceHub.signInitialTransaction(transactionBuilder, listOfNotNull(ourIdentity.owningKey, myKey))

            //Collect signs
            progressTracker.currentStep = GATHERING_SIGS
            val sessionForAccountToSendTo = initiateFlow(targetAccount.host)
            val accountToMoveToSignature = subFlow(CollectSignatureFlow(locallySignedTx, sessionForAccountToSendTo, targetAcctAnonymousParty.owningKey))
            val signedByCounterParty = locallySignedTx.withAdditionalSignatures(accountToMoveToSignature)

            progressTracker.currentStep = FINALISING_TRANSACTION
            val fullySignedTx = subFlow(FinalityFlow(signedByCounterParty, listOf(sessionForAccountToSendTo).filter { it.counterparty != ourIdentity }))
            val movedState = fullySignedTx.coreTransaction.outRefsOfType(TemplateState::class.java).single()
            return "Transaction send to " + targetAccount.host.name.organisation + "'s " + targetAccount.name + " team."
        }
    }

    @InitiatedBy(AddTransaction::class)
    class SendTransactionResponder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
        @Suspendable
        override fun call() {
            //placeholder to record account information for later use
            val accountMovedTo = AtomicReference<AccountInfo>()

            //extract account information from transaction
            val transactionSigner = object : SignTransactionFlow(counterpartySession) {
                override fun checkTransaction(tx: SignedTransaction) {
                    val keyStateMovedTo = tx.coreTransaction.outRefsOfType(TemplateState::class.java).first().state.data.destination
                    keyStateMovedTo?.let {
                        accountMovedTo.set(accountService.accountInfo(keyStateMovedTo.owningKey)?.state?.data)
                    }
                    if (accountMovedTo.get() == null) {
                        throw IllegalStateException("Account to move to was not found on this node")
                    }
                }
            }
            //record and finalize transaction
            val transaction = subFlow(transactionSigner)
            if (counterpartySession.counterparty != serviceHub.myInfo.legalIdentities.first()) {
                val recievedTx = subFlow(ReceiveFinalityFlow(counterpartySession, expectedTxId = transaction.id, statesToRecord = StatesToRecord.ALL_VISIBLE))
                val accountInfo = accountMovedTo.get()
                if (accountInfo != null) {
                    subFlow(BroadcastToCarbonCopyReceiversFlow(accountInfo, recievedTx.coreTransaction.outRefsOfType(TemplateState::class.java).first()))
                }
            }
        }
    }
}
