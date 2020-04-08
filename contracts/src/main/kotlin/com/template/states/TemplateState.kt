package com.template.states

import com.template.contract.TemplateContract
import com.template.schema.mySchema
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.AnonymousParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.util.*

@BelongsToContract(TemplateContract::class)
data class TemplateState(val invoiceID: UUID,
                         val source: AnonymousParty,
                         val rubberType: String,
                         val volume : Int,
                         val price : Int,
                         val destination: AnonymousParty) : ContractState {
//    override val participants: List<AbstractParty> get() = listOfNotNull(destination,source).map { it }
    override val participants: List<AbstractParty> get() = listOf(destination,source).map{ it }
//        override fun generateMappedObject(schema: MappedSchema): PersistentState {
//        return when (schema) {
//            is mySchema -> mySchema.PersistentSchema(
//
//                    invoiceID = invoiceID.id,
//                    source = source.name.toString,
//                    rubberType = rubberType,
//                    volume = volume,
//                    price = price,
//                    destination = destination.name.toString
//
//            )
//            else -> throw IllegalArgumentException("Unrecognised schema $schema")
//        }
//    }
//    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(mySchema)
}