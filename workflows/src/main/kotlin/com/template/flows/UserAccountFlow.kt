package com.template.flows
//
//import co.paralleluniverse.fibers.Suspendable
//import com.template.contracts.RubberContract
//import com.template.states.UserState
//import net.corda.core.contracts.Command
//import net.corda.core.contracts.requireThat
//import net.corda.core.flows.*
//import net.corda.core.identity.Party
//import net.corda.core.transactions.SignedTransaction
//import net.corda.core.transactions.TransactionBuilder
//import net.corda.core.utilities.ProgressTracker
//
//object UserAccountFlow {
//    @InitiatingFlow
//    @StartableByRPC
//
//    class UserProfile (val name: String,
//                       val certification : String,
//                       val destination : Party) : FlowLogic<SignedTransaction>() {
//        companion object {
//            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction to add new account")
//            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contract constraints.")
//            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with private key.")
//            object GATHERING_SIGS : ProgressTracker.Step("Gathering the destination party.") {
//                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
//            }
//            object FINALISING_TRANSACTION : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
//                override fun childProgressTracker() = FinalityFlow.tracker()
//            }
//            fun tracker() = ProgressTracker(
//                    GENERATING_TRANSACTION,
//                    VERIFYING_TRANSACTION,
//                    SIGNING_TRANSACTION,
//                    GATHERING_SIGS,
//                    FINALISING_TRANSACTION
//            )
//        }
//        override val progressTracker = tracker()
//
//        @Suspendable
//        override fun call() : SignedTransaction {
//
//            val notary = serviceHub.networkMapCache.notaryIdentities.first()
//
//            progressTracker.currentStep = GENERATING_TRANSACTION
//            // Generate register transaction.
//            val userState = UserState(serviceHub.myInfo.legalIdentities.first(), name, certification, destination)
//            val txCommand = Command(RubberContract.UserProfile() , userState.participants.map { it.owningKey })
//            val txBuilder = TransactionBuilder(notary)
//                    .addOutputState(userState,RubberContract.rubberContractId)
//                    .addCommand(txCommand)
//
//            progressTracker.currentStep = VERIFYING_TRANSACTION
//            // Verify that the transaction is valid.
//            txBuilder.verify(serviceHub)
//
//            progressTracker.currentStep = SIGNING_TRANSACTION
//            // Sign the transaction.
//            val partSignedTx = serviceHub.signInitialTransaction(txBuilder)
//
//            progressTracker.currentStep = GATHERING_SIGS
//            // Send the state to the counterparty, and receive it back with their signature.
//            val otherPartySession = initiateFlow(destination)
//            val fullySignedTx = subFlow(CollectSignaturesFlow(partSignedTx, setOf(otherPartySession), GATHERING_SIGS.childProgressTracker()))
//
//            progressTracker.currentStep = FINALISING_TRANSACTION
//            // Notarise and record the transaction in both parties' vaults.
//            return subFlow(FinalityFlow(fullySignedTx, setOf(otherPartySession), FINALISING_TRANSACTION.childProgressTracker()))
//
//
//        }
//    }
//    @InitiatedBy(UserProfile::class)
//    class Acceptor(val otherPartySession: FlowSession) : FlowLogic<SignedTransaction>() {
//        @Suspendable
//        override fun call() : SignedTransaction {
//            val signTransactionFlow = object : SignTransactionFlow(otherPartySession) {
//                override fun checkTransaction(stx: SignedTransaction) = requireThat {
//
//                }
//            }
//            val txId = subFlow(signTransactionFlow).id
//
//            return subFlow(ReceiveFinalityFlow(otherPartySession, expectedTxId = txId))
//
//        }
//    }
//}