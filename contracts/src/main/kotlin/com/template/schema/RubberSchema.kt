package com.template.schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object RubberSchema

object mySchema : MappedSchema(
        schemaFamily = RubberSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentSchema::class.java)) {
    @Entity
    @Table(name = "rubber_states")
    class PersistentSchema(
            @Column(name = "invoiceID")
            var invoiceID: UUID,

            @Column(name = "source")
            var source: String,

            @Column(name = "rubberType")
            var rubberType: String,

            @Column(name = "volume")
            var volume: Int,

            @Column(name = "price")
            var price: Int,

            @Column(name = "destination")
            var destination: String
    ): PersistentState() {
        constructor() : this(
                invoiceID = UUID.randomUUID(),
                source = "",
                rubberType = "",
                volume = 0,
                price = 0,
                destination = ""
        )
    }


}