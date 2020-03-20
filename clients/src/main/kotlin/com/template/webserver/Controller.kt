package com.template.webserver

//import com.template.flow.Flows
import com.template.flows.Flows
import com.template.states.TemplateState
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

val SERVICE_NAMES = listOf("Notary", "Network Map Service")

@RestController
@RequestMapping("/") // The paths for HTTP requests are relative to this base path.
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val myLegalName = rpc.proxy.nodeInfo().legalIdentities.first().name
    private val proxy = rpc.proxy

//    @GetMapping(value = "/templateendpoint", produces = arrayOf("text/plain"))
//    private fun templateendpoint(): String {
//        return "Define an endpoint here.PeterLi"
//    }

    // Reture node's name
    @GetMapping(value = [ "me" ])
    fun whoami() = mapOf("me" to myLegalName)

    @GetMapping(value = "getAllRecord", produces = ["text/plain"])
    private fun getAllBasicRecord() = proxy.vaultQueryBy<TemplateState>().states.toString()


    @PostMapping(value = "addRecord" , headers = [ "Content-Type=multipart/form-data" ])
    fun addBasicRecord(request: HttpServletRequest): ResponseEntity<String> {
//        val source = request.getParameter("Source").toString()
        val rubberType = request.getParameter("Rubber type").toString()
        val volume = request.getParameter("Volume").toInt()
        val price = request.getParameter("Price").toInt()

        val destination = request.getParameter("Destination")
        val destinationParty = CordaX500Name.parse(destination)
        return ResponseEntity.badRequest().body("Party named $destination cannot be found.\n")

//        return try {
//
//            val signedTx = proxy.startTrackedFlow(::Flows, source rubberType, volume, price, destinationParty).returnValue.getOrThrow()
//            ResponseEntity.status(HttpStatus.CREATED).body("Add Record successfully.")
//
//        } catch (ex: Throwable) {
//            logger.error(ex.message, ex)
//            ResponseEntity.badRequest().body(ex.message!!)
//        }
    }
}