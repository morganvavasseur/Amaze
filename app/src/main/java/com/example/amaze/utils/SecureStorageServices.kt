package com.example.amaze.utils

import com.chamber.java.library.SharedChamber
import com.chamber.java.library.model.ChamberType
import com.example.amaze.AmazeApp
import com.example.amaze.models.User
import com.google.gson.Gson

class SecureStorageServices {

    companion object {
        private var sharedChamber : SharedChamber<*> = SharedChamber
            .ChamberBuilder(AmazeApp.sharedInstance)
            .setChamberType(ChamberType.KEY_256)
            .enableCrypto(true, true)
            .enableKeyPrefix(true, "SECRET_")
            .setPassword("CLEAR_PASSWORD")
            .setFolderName("secure")
            .buildChamber()

        private val AUTH_JWT_TOKEN_KEY = "TOKEN_KEY"
        private val AUTH_USER_KEY = "USER_KEY"

        var authJwtToken : String?
        get() = sharedChamber.getString(AUTH_JWT_TOKEN_KEY, null)
        set(value) {
            sharedChamber.put(AUTH_JWT_TOKEN_KEY, value)
        }

        var authUser : User?
            get() {
                val gson : Gson = Gson()
                val gsonData = sharedChamber.getModel(AUTH_USER_KEY , User::class.java).toString()
                val user = gson.fromJson(gsonData, User::class.java)
                return user
            }
                set(value) {
                    val gson : Gson = Gson()
                    sharedChamber.putModel(AUTH_USER_KEY, gson.toJson(value))
                }
            }
    }
