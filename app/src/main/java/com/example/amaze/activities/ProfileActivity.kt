package com.example.amaze.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.amaze.AmazeApp
import com.example.amaze.MainActivity
import com.example.amaze.R
import com.example.amaze.adapters.ProfilePagerAdapter
import com.example.amaze.utils.SecureStorageServices
import kotlinx.android.synthetic.main.activity_basket_empty.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val adapter = ProfilePagerAdapter(supportFragmentManager)

        profile_viewPager.adapter = adapter

        profile_tabLayout.setupWithViewPager(profile_viewPager)

        var user = SecureStorageServices.authUser
//        imageViewProfilePicture = user.profilePicture
        textViewProfileName?.text = user?.firstName + " " + user?.lastName

        profile_back_button.setOnClickListener({back()})
    }

    fun back() {
        var intent = Intent(AmazeApp.sharedInstance, MainActivity::class.java)
        startActivity(intent)
    }
}