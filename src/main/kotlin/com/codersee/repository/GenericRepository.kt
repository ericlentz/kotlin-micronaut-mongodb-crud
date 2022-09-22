package com.codersee.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import io.micronaut.context.annotation.Property
import org.bson.types.ObjectId

/**
 * Implementation of typically desired interactions with the database
 *
 * @param T the model object type
 */
abstract class GenericRepository<T> {

    @field:Property(name = "app.db.name")
    var database: String = ""

    fun create(t: T): InsertOneResult = getCollection().insertOne(t)

    fun findAll(): List<T> = getCollection().find().toList()

    fun findById(id: String): T? = getCollection().find(Filters.eq("_id", ObjectId(id)))
        .toList()
        .firstOrNull()

    fun update(id: String, update: T): UpdateResult = getCollection().replaceOne(
        Filters.eq("_id", ObjectId(id)), update
    )

    fun deleteById(id: String): DeleteResult = getCollection().deleteOne(Filters.eq("_id", ObjectId(id)))

    abstract fun getCollection(): MongoCollection<T>

}
