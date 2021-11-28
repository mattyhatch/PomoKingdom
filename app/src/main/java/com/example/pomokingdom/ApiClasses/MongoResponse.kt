package com.example.pomokingdom.ApiClasses

import kotlinx.serialization.Serializable

@Serializable
data class MongoResponse(private val acknowledged:Boolean? = null,
private val modifiedCount:Int? = null,
private val upsertedId:Int? = null,
private val upsertedCount:Int? = null,
private val matchedCount:Int? = null)
