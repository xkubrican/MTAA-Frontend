package com.example.yourslovakiafrontend.models

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import fiit.mtaa.yourslovakia.models.Castle
import fiit.mtaa.yourslovakia.models.GenericPointOfInterestModel
import fiit.mtaa.yourslovakia.models.Peak
import fiit.mtaa.yourslovakia.models.PlaceOfWorship
import fiit.mtaa.yourslovakia.models.PointOfInterest
import java.lang.reflect.Type

class PointOfInterestAdapter : JsonDeserializer<PointOfInterest> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): PointOfInterest {
        val jsonObject = json.asJsonObject

        // Check for attributes that indicate specific types
        return when {
            jsonObject.has("castleType") -> context.deserialize(json, Castle::class.java)
            jsonObject.has("elevation") -> context.deserialize(json, Peak::class.java)
            jsonObject.has("religion") -> context.deserialize(json, PlaceOfWorship::class.java)
            jsonObject.has("type") -> context.deserialize(
                json,
                GenericPointOfInterestModel::class.java
            )

            else -> throw JsonParseException("Cannot determine the type of PointOfInterest from JSON")
        }
    }
}
