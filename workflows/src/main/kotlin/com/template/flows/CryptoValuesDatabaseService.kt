package com.template.flows

import net.corda.core.node.ServiceHub
import net.corda.core.node.services.CordaService
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import kotlin.collections.ArrayList


/**
 * A database service subclass for handling a table of crypto values.
 *
 * @param services The node's service hub.
 */
@CordaService
class CryptoValuesDatabaseService(services: ServiceHub) : DatabaseService(services) {
    init {
        setUpStorage()
    }


    /**
     * Adds a crypto token and associated value to the table of crypto values.
     */
    // Making addTokenValue unique for each token
    fun addDatabaseValue(name: String, email: String, date: Long, contact: Int, country: String) {
//        val query = "select name from $TABLE_NAME2 where email = ?"
//
//        val params = mapOf(1 to email)
//
//        val results = executeQuery(query, params) { it -> it.getString("name") }
//
//        if (results.isEmpty()) {
//            val query = "insert into $TABLE_NAME2 values(?, ?, ?, ?, ?)"
//
//            val params = mapOf(1 to name, 2 to email, 3 to date, 4 to contact, 5 to country)
//
//            executeUpdate(query, params)
//            log.info("Data $email added to crypto_values table.")
//        }
//        else
//        {
//            throw IllegalArgumentException("Data email $email already available in database make sure a unique email id")
//
//        }

    }

    /**
     * Updates the value of a crypto token in the table of crypto values.
     */
    fun updateDatabaseValue(name: String, email: String) {
//        val query = "select email from $TABLE_NAME2 where name = ?"
//
//        val params = mapOf(1 to name)
//
//        val results = executeQuery(query, params) { it -> it.getString("email") }
//
//        if (results.isEmpty()) {
//            throw IllegalArgumentException("Data $name not available in database")
//        }
//        else
//        {
//            val query = "update $TABLE_NAME2 set email = ? where name = ?"
//
//            val params = mapOf(1 to email, 2 to name)
//
//            executeUpdate(query, params)
//            log.info("Data $name updated in user_data table.")
//        }

    }

    // Deleting Data values from table
    fun deleteDatabaseValue(name: String) {
//        val query = "delete from $TABLE_NAME2 where name = ?;"
//
//        val params = mapOf(1 to name)
//
//        executeUpdate(query, params)
//        log.info("Data of  $name deleted in user_data table.")
    }

    @Throws(SQLException::class)
    fun resultSetToArrayList(rs: ResultSet): List<Any?>{
        val md = rs.metaData
        val columns = md.columnCount
        val list: ArrayList<Any?> = ArrayList<Any?>(50)
        while (rs.next()) {
            val row: HashMap<Any?, Any?> = HashMap<Any?, Any?>(columns)
            for (i in 1..columns) {
                row[md.getColumnName(i)] = rs.getObject(i)
            }
            list.add(row)
        }
        return list
    }
    /**
     * Retrieves the value of a crypto token in the table of crypto values.
     */
//    fun queryDatabaseValue(email: String): HashMap<Any?, Any?> {
//        val query = "select * from $TABLE_NAME2 where email = ?"
//
//        val params = mapOf(1 to email)
//
//        val results = executeQuery2(query, params)
////        val md = results.single()
////      //  val columns = md
////        //val res = resultSetToArrayList(results.single())
////        if (results.isEmpty()) {
////            throw IllegalArgumentException("Mail $email not present in database.")
////        }
////
////        val name = results.single()
//        log.info("Mail $email read from crypto_values table.")
//        return results
//    }

    private fun setUpStorage() {
//        val query = """
//            create table if not exists $TABLE_NAME2(
//                name varchar(64),
//                email varchar(64),
//                date long,
//                contact integer,
//                country varchar(64)
//            )"""
//
//        executeUpdate(query, emptyMap())
//        log.info("Created $TABLE_NAME2 table.")
    }
}