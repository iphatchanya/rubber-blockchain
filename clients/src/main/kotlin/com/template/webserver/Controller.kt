package com.template.webserver

import com.r3.corda.lib.accounts.workflows.flows.AllAccounts
import com.template.flows.CreateNewAccount
import com.template.flows.ShareAccount
import com.template.flows.TransactionFlow.AddTransaction
import com.template.states.TransactionState
import net.corda.core.identity.CordaX500Name
import net.corda.core.internal.declaredField
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.utilities.getOrThrow
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/blockchainTransaction/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy
    private val myLegalName = rpc.proxy.nodeInfo().legalIdentities.first().name

    // Reture node's name
    @GetMapping(value = [ "me" ])
    fun whoami() = mapOf("me" to myLegalName)

    @GetMapping(value = [ "peers" ], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPeers(): Map<String, List<CordaX500Name>> {
        val nodeInfo = proxy.networkMapSnapshot()
        return mapOf("peers" to nodeInfo
                .map { it.legalIdentities.first().name }
                //filter out myself, notary and eventual network map started by driver
                .filter { it.organisation !in (listOf("Admin", "Network Map Service") + myLegalName.organisation) })
    }

    @GetMapping(value = "getAllUser", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllUser() = proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {
        "[ AccountInfo : Host = " + it.state.data.host +
        ", ID = " + it.state.data.identifier.id +
        ", Name = " + it.state.data.name + " ]"}


    @GetMapping(value = "getAllTransactionByUser", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllTransactionByUser() : ResponseEntity<List<String>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<TransactionState>().states.map {
                "[ Invoice State : Invoice ID = " + it.state.data.invoiceID +
                ", Source = " + it.state.data.source +
                ", Rubber type = " + it.state.data.rubberType +
                ", Volume = " + it.state.data.volume +
                ", Price = " + it.state.data.price +
                ", Destination = " + it.state.data.destination + " ]"})
    }

    @GetMapping(value = "getAllTransactionByUser2", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllTransactionByUser2() : ResponseEntity<List<String>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<TransactionState>().states.map {
            "[ Invoice State : Invoice ID = " + it.state.data.invoiceID +
                    ", Source = " + it.state.data.source.equals(proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name}) +
                    ", Rubber type = " + it.state.data.rubberType +
                    ", Volume = " + it.state.data.volume +
                    ", Price = " + it.state.data.price +
                    ", Destination = " + proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name}.contains(it.state.data.destination.toString()) + " ]"})
    }

    @GetMapping(value = "getAllTransactionByUser3", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllTransactionByUser3() : ResponseEntity<List<String>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<TransactionState>().states.map {
            "[ Invoice State : Invoice ID = " + it.state.data.invoiceID +
                    ", Source = " + it.state.data.source.also { proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name} } +
                    ", Rubber type = " + it.state.data.rubberType +
                    ", Volume = " + it.state.data.volume +
                    ", Price = " + it.state.data.price +
                    ", Destination = " + it.state.data.destination.apply { proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name} } + " ]"})
    }


    @GetMapping(value = "getAllTransactionByUser4", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllTransactionByUser4() : ResponseEntity<List<String>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<TransactionState>().states.map {
            "[ Invoice State : Invoice ID = " + it.state.data.invoiceID +
                    ", Source = " + it.state.data.source.to(proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name}) +
                    ", Rubber type = " + it.state.data.rubberType +
                    ", Volume = " + it.state.data.volume +
                    ", Price = " + it.state.data.price +
                    ", Destination = " + it.state.data.destination.toString().to(proxy.startFlowDynamic(AllAccounts::class.java).returnValue.getOrThrow().map {it.state.data.name}) + " ]"})
    }

    @GetMapping(value = "getAllTransactionByAdmin", produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getAllTransactionByAdmin() : ResponseEntity<List<String>> {
        return ResponseEntity.ok(proxy.vaultQueryBy<TransactionState>().states.map {
                "[ State Info : " + it.state.toString() +
                ", Invoice State : Invoice ID = " + it.state.data.invoiceID +
                ", Source = " + it.state.data.source +
                ", Rubber type = " + it.state.data.rubberType +
                ", Volume = " + it.state.data.volume +
                ", Price = " + it.state.data.price +
                ", Destination = " + it.state.data.destination + " ]"})
    }

    @GetMapping(value = [ "getMyTransaction" ], produces = ["text/plain" ])
    private fun getMyTransaction() = proxy.vaultQueryBy<TransactionState>().states.filter { it.state.data.source.equals(proxy.nodeInfo().legalIdentities.first()) }

    @GetMapping(value = [ "getMyTransaction2" ], produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getMyTransaction2() = proxy.vaultQueryBy<TransactionState>().states.filter { it.state.data.equals(proxy.nodeInfo().legalIdentities.first()) }

    @GetMapping(value = [ "getMyTransaction3" ], produces = [MediaType.APPLICATION_JSON_VALUE ])
    private fun getMyTransaction3() = proxy.vaultQueryBy<TransactionState>().states.map {
            " Invoice State : Invoice ID = " + it.state.data.invoiceID +
            ", Source = " + it.state.data.source +
            ", Rubber type = " + it.state.data.rubberType +
            ", Volume = " + it.state.data.volume +
            ", Price = " + it.state.data.price +
            ", Destination = " + it.state.data.destination }


    @PostMapping(value = "createNewAccount" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
    fun createNewAccount(request: HttpServletRequest): ResponseEntity<String> {
        val accountName = request.getParameter("accountName").toString()

        return try {
            val signedTx = proxy.startTrackedFlow(::CreateNewAccount, accountName).returnValue.getOrThrow()
            ResponseEntity.status(HttpStatus.CREATED).header("Access-Control-Allow-Origin","*").body("{\"status\":\"OK\"}")

        } catch (ex: Throwable) {
            logger.error(ex.message, ex)
            ResponseEntity.badRequest().body(ex.message!!)
        }
    }

    @PostMapping(value = "shareAccount" , headers = [ "Content-Type=application/x-www-form-urlencoded" ])
    fun shareAccount(request: HttpServletRequest): ResponseEntity<String> {
        val accountNameShared = request.getParameter("accountNameShared").toString()
        val shareTo = request.getParameter("shareTo")
        val shareToParty = CordaX500Name.parse(shareTo)
        val partyOfShareTo =  proxy.wellKnownPartyFromX500Name(shareToParty) ?:
        return ResponseEntity.badRequest().body("Party named $shareTo cannot be found.\n")

        return try {
            val signedTx = proxy.startTrackedFlow(::ShareAccount, accountNameShared, partyOfShareTo).returnValue.getOrThrow()
            ResponseEntity.status(HttpStatus.CREATED).header("Access-Control-Allow-Origin","*").body("{\"status\":\"OK\"}")

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
            ResponseEntity.status(HttpStatus.CREATED).header("Access-Control-Allow-Origin","*").body("{\"status\":\"OK\"}")

        } catch (ex: Throwable) {
            logger.error(ex.message, ex)
            ResponseEntity.badRequest().body(ex.message!!)
        }
    }
}