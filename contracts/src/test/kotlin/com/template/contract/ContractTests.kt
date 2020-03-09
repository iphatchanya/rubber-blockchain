package com.template.contract

import com.template.contract.TemplateContract
import com.template.states.TemplateState
import net.corda.core.identity.AnonymousParty
import net.corda.core.identity.CordaX500Name
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import net.corda.testing.node.ledger
import org.junit.Test
import java.util.*

class ContractTests {
    private val ledgerServices = MockServices(listOf("com.template.contract"))
    private val sourceParty = TestIdentity(CordaX500Name("SourceParty", "London", "GB"))
    private val destinationParty = TestIdentity(CordaX500Name("DestinationParty", "New York", "US"))
    private val flowRubberType = "Rubber"
    private val flowVolume = 20
    private val flowPrice = 600

//    @Test
//    fun `transaction must include Create command`() {
//        ledgerServices.ledger {
//            transaction {
//                output(TemplateContract.ID, TemplateState())
//                fails()
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                verifies()
//            }
//        }
//    }
//
//    @Test
//    fun `transaction must have no inputs`() {
//        ledgerServices.ledger {
//            transaction {
//                input(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, flowPrice, destinationParty.party))
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, flowPrice, destinationParty.party))
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                failsWith("No input when create.")
//            }  }
//
//    }
//
//    @Test
//    fun `transaction must have one output`() {
//        ledgerServices.ledger {
//            transaction {
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, flowPrice, destinationParty.party))
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, flowPrice, destinationParty.party))
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                failsWith("Only one output state should be created.")
//            }
//        }
//    }
//
//    @Test
//    fun `the source is not the destination`() {
//        ledgerServices.ledger {
//            transaction {
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, flowPrice, sourceParty.party))
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                failsWith("The source and the destination cannot be same entity.")
//            }
//        }
//    }
//
//    @Test
//    fun `The volume must greater than 0`() {
//        ledgerServices.ledger {
//            transaction {
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, 0, flowPrice, destinationParty.party))
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                failsWith("The volume must greater than 0.")
//            }
//        }
//    }
//
//    @Test
//    fun `The price must greater than 0`() {
//        ledgerServices.ledger {
//            transaction {
//                output(TemplateContract.ID, TemplateState(sourceParty.party, flowRubberType, flowVolume, 0, destinationParty.party))
//                command(listOf(sourceParty.publicKey, destinationParty.publicKey), TemplateContract.Commands.Create())
//                failsWith("The price must greater than 0.")
//            }
//        }
//    }


}