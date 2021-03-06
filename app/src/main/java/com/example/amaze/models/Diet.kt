package com.example.amaze.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.jar.Attributes


@JsonIgnoreProperties(ignoreUnknown = true)
open class Diet {

    @JsonProperty("_id")
    lateinit var id: String

    lateinit var name: String
    lateinit var description: String
    open lateinit var users: ArrayList<User>
}