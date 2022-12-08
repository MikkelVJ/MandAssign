package com.example.mandatoryassignment.Models

class Cat(val id: Int, val name: String, val description: String, val place: String, val reward: String, val userId: String?, val date: Long, val pictureUrl: String?) {
// implement constructor() ?

    override fun toString(): String {
        return ("$name $description")
    }
}
