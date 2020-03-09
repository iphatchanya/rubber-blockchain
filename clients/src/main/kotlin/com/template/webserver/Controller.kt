package com.template.webserver

//import com.template.flow.Flows
import com.template.states.TemplateState
import net.corda.core.contracts.StateAndRef
import net.corda.core.identity.CordaX500Name
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

//    private val myLegalName = rpc.proxy.nodeInfo().legalIdentities.first().name
    private val proxy = rpc.proxy

    @GetMapping(value = "/templateendpoint", produces = arrayOf("text/plain"))
    private fun templateendpoint(): String {
        return "Define an endpoint here.PeterLi"
    }

//    // Reture node's name
//    @GetMapping(value = [ "me" ], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun whoami() = mapOf("me" to myLegalName)
//
//    // Reture all parties registered with the network map service.
//    @GetMapping(value = [ "peers" ], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getPeers(): Map<String, List<CordaX500Name>> {
//        val nodeInfo = proxy.networkMapSnapshot()
//        return mapOf("peers" to nodeInfo
//                .map { it.legalIdentities.first().name }
//                //filter out myself, notary and eventual network map started by driver
//                .filter { it.organisation !in (SERVICE_NAMES + myLegalName.organisation) })
//    }
//
//    // Display all state that exist in the node's vault.
//    @GetMapping(value = [ "ious" ], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getIOUs() : ResponseEntity<List<StateAndRef<TemplateState>>> {
//        return ResponseEntity.ok(proxy.vaultQueryBy<TemplateState>().states)
//    }

    // Initiates a flow to agree an IOU between two parties.
//    @PostMapping(value = [ "create-iou" ], produces = [MediaType.TEXT_PLAIN_VALUE], headers = [ "Content-Type=application/x-www-form-urlencoded" ])
//    fun createIOU(request: HttpServletRequest): ResponseEntity<String> {
//        println(request.parameterMap.toString())
//        val iouValue = request.getParameter("iouValue").toInt()
//        val partyName = request.getParameter("partyName")
//        if(partyName == null){
//            return ResponseEntity.badRequest().body("Query parameter 'partyName' must not be null.\n")
//        }
//        if (iouValue <= 0 ) {
//            return ResponseEntity.badRequest().body("Query parameter 'iouValue' must be non-negative.\n")
//        }
//        val partyX500Name = CordaX500Name.parse(partyName)
//        val otherParty = proxy.wellKnownPartyFromX500Name(partyX500Name) ?: return ResponseEntity.badRequest().body("Party named $partyName cannot be found.\n")
//
//        return try {
//            val iouId = proxy.startFlowDynamic(Flows.Initiator::class.java, iouValue, otherParty).returnValue.getOrThrow()
//            ResponseEntity.status(HttpStatus.CREATED).body("IOU transaction ${iouId}.\n")

//        } catch (ex: Throwable) {
//            logger.error(ex.message, ex)
//            ResponseEntity.badRequest().body(ex.message!!)
//        }
//    }

//    // Displays all states that only this node has been involved in.
//    @GetMapping(value = [ "my-ious" ], produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getMyIOUs(): ResponseEntity<List<StateAndRef<TemplateState>>>  {
//        val myious = proxy.vaultQueryBy<TemplateState>().states.filter { it.state.data.rubberType.equals(proxy.nodeInfo().legalIdentities.first()) }
//        return ResponseEntity.ok(myious)
//    }
}