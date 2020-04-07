package com.example.r6tabmobileapp.api

class User (val id : String, val emailAddress : String, val userName : String, val password : String) {
    constructor() : this ("", "", "", "")
}