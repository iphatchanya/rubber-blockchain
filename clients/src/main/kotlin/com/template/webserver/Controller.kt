package com.template.webserver

//import com.template.flow.Flows
import com.template.flows.CreateNewAccount
import com.template.flows.ShareAccount
import com.template.flows.TransactionFlow.AddTransaction
//import com.template.flows.UserAccountFlow.UserProfile
//import com.template.flows.RubberFlow.NewRecord
import com.template.states.TemplateState
import com.template.states.TransactionRecordState
import com.template.states.UserState
import net.corda.core.contracts.StateAndRef
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.utilities.getOrThrow
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

//val SERVICE_NAMES = listOf("Notary", "Network Map Service")

@RestController
@RequestMapping("/blockchainTransaction/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy
    private val myLegalName = rpc.proxy.nodeInfo().legalIdentities.first().name

//    @GetMapping(value = "/templateendpoint", produces = arrayOf("text/plain"))
//    private fun templateendpoint(): String {
//        return "Define an endpoint here.PeterLi"
//    }

    // Reture node's name
    @GetMapping(value = [ "me" ])
    fun whoami() = mapOf("me" to myLegalName)

//    @GetMapping(value = [ "peers" ], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getPeers(): Map<String, List<CordaX500Name>> {
//        val nodeInfo = proxy.networkMapSnapshot()
//        return mapOf("peers" to nodeInfo
//                .map { it.legalIdentities.first().name }
//                //filter out myself, notary and eventual network map started by driver
//                .filter { it.organisation !in (SERVICE_NAMES + myLegalName.organisation) })
//    }

    @GetMapping(value = "getAllUser", produces = ["text/plain"])
    private fun getAllUser() = proxy.vaultQueryBy<UserState>().states.toString()

    @GetMapping(value = "getAllTransaction", produces = ["text/plain"])
    private fun getAllTransation() = proxy.vaultQueryBy<TransactionRecordState>().states.toString()

//    @GetMapping(value = "getAllRecord", produces = ["text/plain"])
//    private fun getAllRecord() = proxy.vaultQueryBy<TemplateState>().states.toString()

//    @PostMapping(value = "users" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
//    fun users(request: HttpServletRequest): ResponseEntity<String> {
//        val name = request.getParameter("name").toString()
//        val certification = request.getParameter("certification").toString()
//        val destination = request.getParameter("destination")
//        val destinationParty = CordaX500Name.parse(destination)
//        val partyDestination = proxy.wellKnownPartyFromX500Name(destinationParty) ?:
//        return ResponseEntity.badRequest().body("Party named $destination cannot be found.\n")
//
//        return try {
//            val signedTx = proxy.startTrackedFlow(::UserProfile, name, certification, partyDestination).returnValue.getOrThrow()
//            ResponseEntity.status(HttpStatus.CREATED).body("Register successfully.")
//
//        } catch (ex: Throwable) {
//            logger.error(ex.message, ex)
//            ResponseEntity.badRequest().body(ex.message!!)
//        }
//    }
//
//    @PostMapping(value = "addRecord" , headers = [ "Content-Type=multipart/form-data" ])
//    fun addRecord(request: HttpServletRequest): ResponseEntity<String> {
//        val rubberType = request.getParameter("rubberType").toString()
//        val volume = request.getParameter("volume").toInt()
//        val price = request.getParameter("price").toInt()
//        val destination = request.getParameter("destination")
//        val destinationParty = CordaX500Name.parse(destination)
//        val partyDestination = proxy.wellKnownPartyFromX500Name(destinationParty) ?:
//        return ResponseEntity.badRequest().body("Party named $destination cannot be found.\n")
//
//        return try {
//            val signedTx = proxy.startTrackedFlow(::NewRecord, rubberType, volume, price, partyDestination).returnValue.getOrThrow()
//            ResponseEntity.status(HttpStatus.CREATED).body("Register successfully.")
//
//        } catch (ex: Throwable) {
//            logger.error(ex.message, ex)
//            ResponseEntity.badRequest().body(ex.message!!)
//        }
//    }

    @PostMapping(value = "createNewAccount" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
    fun createNewAccount(request: HttpServletRequest): ResponseEntity<String> {
        val accountName = request.getParameter("accountName").toString()

        return try {
            val signedTx = proxy.startTrackedFlow(::CreateNewAccount, accountName).returnValue.getOrThrow()
            ResponseEntity.status(HttpStatus.CREATED).body("Add Record successfully.")

        } catch (ex: Throwable) {
            logger.error(ex.message, ex)
            ResponseEntity.badRequest().body(ex.message!!)
        }
    }

    @PostMapping(value = "shareAccount" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
    fun shareAccount(request: HttpServletRequest): ResponseEntity<String> {
        val accountNameShared = request.getParameter("accountNameShared").toString()
        val shareTo = request.getParameter("shareTo").toString()
        val shareToParty = CordaX500Name.parse(shareTo)
        val partyOfShareTo =  proxy.wellKnownPartyFromX500Name(shareToParty) ?:
        return ResponseEntity.badRequest().body("Party named $shareTo cannot be found.\n")

        return try {
            val signedTx = proxy.startTrackedFlow(::ShareAccount, accountNameShared, partyOfShareTo).returnValue.getOrThrow()
            ResponseEntity.status(HttpStatus.CREATED).body("Add Record successfully.")

        } catch (ex: Throwable) {
            logger.error(ex.message, ex)
            ResponseEntity.badRequest().body(ex.message!!)
        }
    }

    @PostMapping(value = "addRecordToTransaction" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
    fun addRecordToTransaction(request: HttpServletRequest): ResponseEntity<String> {
        val source = request.getParameter("source").toString()
        val rubberType = request.getParameter("rubberType").toString()
        val volume = request.getParameter("volume").toInt()
        val price = request.getParameter("price").toInt()
        val destination = request.getParameter("destination").toString()

        return try {
            val signedTx = proxy.startTrackedFlow(::AddTransaction, source, rubberType, volume, price, destination).returnValue.getOrThrow()
            ResponseEntity.status(HttpStatus.CREATED).body("Add Record successfully.")

        } catch (ex: Throwable) {
            logger.error(ex.message, ex)
            ResponseEntity.badRequest().body(ex.message!!)
        }
    }
}