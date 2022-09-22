package com.codersee.service

import com.codersee.exception.NotFoundException
import com.codersee.repository.GenericRepository
import org.bson.BsonValue

/**
 * Common service functions necessary for interacting with the database.
 *
 * @param T the model object type
 * @param U the request object passed in from the controller typically having many of the model fields
 *
 * @constructor pass the repository this service will interact with
 */
abstract class GenericService<T, U>(private val repository: GenericRepository<T>) {

    abstract var entityName: String

    open fun create(request: U): BsonValue? {
        val insertedCompany = repository.create(
            createModelInstanceFromRequest(request)
        )
        return insertedCompany.insertedId
    }

    open fun findAll(): List<T> {
        return repository.findAll()
    }

    open fun findById(id: String): T {
        return repository.findById(id)
            ?: throw NotFoundException("$entityName with id $id was not found")
    }

    open fun update(id: String, request: U): T {
        val updateResult = repository.update(
            id = id,
            update = createModelInstanceFromRequest(request)
        )

        if (updateResult.modifiedCount == 0L)
            throw throw RuntimeException("$entityName with id $id was not updated")

        return findById(id)
    }

    open fun deleteById(id: String) {
        val deleteResult = repository.deleteById(id)

        if (deleteResult.deletedCount == 0L)
            throw throw RuntimeException("$entityName with id $id was not deleted")
    }

    abstract fun createModelInstanceFromRequest(request: U): T
}