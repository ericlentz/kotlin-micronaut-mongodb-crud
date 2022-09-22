package com.codersee.repository

import com.codersee.model.Company
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import jakarta.inject.Singleton

@Singleton
class CompanyRepository(private val mongoClient: MongoClient) : GenericRepository<Company>() {

    override fun getCollection(): MongoCollection<Company> = mongoClient
        .getDatabase(database)
        .getCollection("company", Company::class.java)
}