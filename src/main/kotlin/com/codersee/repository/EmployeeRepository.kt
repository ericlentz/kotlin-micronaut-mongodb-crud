package com.codersee.repository

import com.codersee.model.Employee
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import jakarta.inject.Singleton
import org.bson.types.ObjectId

@Singleton
class EmployeeRepository(private val mongoClient: MongoClient): GenericRepository<Employee>() {

    fun findAllByCompanyId(companyId: String): List<Employee> =
        getCollection()
            .find(
                Filters.eq("company._id", ObjectId(companyId))
            )
            .toList()

    override fun getCollection(): MongoCollection<Employee> =
        mongoClient
            .getDatabase(database)
            .getCollection("employee", Employee::class.java)
}