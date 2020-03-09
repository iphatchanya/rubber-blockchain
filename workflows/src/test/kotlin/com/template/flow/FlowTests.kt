package com.template.test.flow

import com.template.flow.Flows
import com.template.states.TemplateState
import net.corda.core.contracts.TransactionVerificationException
import net.corda.core.node.services.queryBy
import net.corda.core.utilities.getOrThrow
import net.corda.testing.core.singleIdentity
import net.corda.testing.internal.chooseIdentity
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkParameters
import net.corda.testing.node.StartedMockNode
import net.corda.testing.node.TestCordapp
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FlowTests {

    private lateinit var network: MockNetwork
    private lateinit var a: StartedMockNode
    private lateinit var b: StartedMockNode

    @Before
    fun setup() {
        network = MockNetwork(MockNetworkParameters(cordappsForAllNodes = listOf(
                TestCordapp.findCordapp("com.template.contract"),
                TestCordapp.findCordapp("com.template.flow")
        )))
        a = network.createPartyNode()
        b = network.createPartyNode()

        listOf(a, b).forEach { it.registerInitiatedFlow(Flows::class.java) }
        network.runNetwork()
    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

//    @Test
//    fun `flow rejects invalid`() {
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//
//        // The TemplateContract specifies that it cannot have negative values.
//        assertFailsWith<TransactionVerificationException> { future.getOrThrow() }
//    }

//    @Test
//    fun `SignedTransaction returned by the flow is signed by the initiator`() {
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//
//        val signedTx = future.getOrThrow()
//        signedTx.verifySignaturesExcept(b.info.singleIdentity().owningKey)
//    }
//
//    @Test
//    fun `SignedTransaction returned by the flow is signed by the acceptor`() {
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//
//        val signedTx = future.getOrThrow()
//        signedTx.verifySignaturesExcept(a.info.singleIdentity().owningKey)
//    }
//
//    @Test
//    fun `flow records a transaction in both parties' transaction storage`() {
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//        val signedTx = future.getOrThrow()
//
////        // We check the recorded transaction in both transaction storages.
//        for (node in listOf(a, b)) {
//            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
//        }
//    }
//
//    @Test
//    fun `recorded transaction has no inputs and a single output`() {
//        val flowRubberType = "1"
//        val flowVolume = 10
//        val flowPrice = 200
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//        val signedTx = future.getOrThrow()
//
//        // We check the recorded transaction in both vaults.
//        for (node in listOf(a, b)) {
//            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id)
//            val txOutputs = recordedTx!!.tx.outputs
//            val txInputs = recordedTx!!.tx.inputs // get input
//            assert(txOutputs.size == 1)
//            assert(txInputs.isEmpty())
//
//
//
//            val recordedState = txOutputs[0].data as TemplateState
//            assertEquals(recordedState.source, a.info.singleIdentity())
//            assertEquals(recordedState.rubberType, flowRubberType)
//            assertEquals(recordedState.volume, flowVolume)
//            assertEquals(recordedState.price, flowPrice)
//            assertEquals(recordedState.destination, b.info.singleIdentity())
//        }
//    }
//    @Test
//    fun `flow records the correct state in both parties' vaults`() {
//        val flowRubberType = "1"
//        val flowVolume = 10
//        val flowPrice = 200
//        val flow = Flows("","1",-10,0,"")
//        val future = a.startFlow(flow)
//        network.runNetwork()
//        future.getOrThrow()
//
//        // We check the recorded in both vaults.
//        for (node in listOf(a, b)) {
//            node.transaction {
//                val stateList = node.services.vaultService.queryBy<TemplateState>().states
//                assertEquals(1, stateList.size)
//                val recordedState = stateList.single().state.data
//                assertEquals(recordedState.source, a.info.singleIdentity())
//                assertEquals(recordedState.rubberType, flowRubberType)
//                assertEquals(recordedState.volume, flowVolume)
//                assertEquals(recordedState.price, flowPrice)
//                assertEquals(recordedState.destination, b.info.singleIdentity())
//            }
//        }
//    }
}