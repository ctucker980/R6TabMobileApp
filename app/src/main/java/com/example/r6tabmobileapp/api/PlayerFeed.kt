package com.example.r6tabmobileapp.api

import com.google.gson.JsonObject

class PlayerFeed (val status: Int, val foundmatch: Boolean, val requested: String, val player: JsonObject, val stats : JsonObject, val ranked : JsonObject)