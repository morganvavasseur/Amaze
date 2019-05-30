/*
 * Developed by Yann Malanda on 5/30/19 11:24 AM.
 * Last modified 5/30/19 11:24 AM
 * Copyright (c) 2019.
 *
 */

package com.example.amaze.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amaze.AmazeApp

import com.example.amaze.R
import com.example.amaze.activities.AddGuestToEventActivity
import com.example.amaze.activities.CreateEventActivity
import com.example.amaze.components.AmazeNextButton
import com.example.amaze.models.Event
import com.example.amaze.network.AuthUser
import com.example.amaze.network.SendableEvent
import com.example.amaze.utils.SecureStorageServices
import kotlinx.android.synthetic.main.fragment_event__params.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EventParamsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EventParamsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EventParamsFragment : Fragment(), AmazeNextButton.OnNextButtonListener {
    // TODO: Rename and change types of parameters
    private var event: SendableEvent? = null
    private var listener: OnEventParamsListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable(ARG_PARAM1) as SendableEvent
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event__params, container, false)
    }

    override fun onStart() {
        super.onStart()

        createEventNextButton.setNextButtonOnClickListener(this)
        event_creation_date.setOnClickListener {useDatePicker()}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEventParamsListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onNextButtonClick() {

        // Initialise event
        event?.title = event_creation_title.text.toString()
        event?.date = event_creation_date.text.toString() // A CHANGER
        event?.location = event_creation_location.text.toString()
        event?.description = eventCreationDescription.text
        event?.entrancePrice = Integer.parseInt(event_creation_price.text.toString())
        event?.organizers = getHostFromAuthenticatedUser()
        listener?.onParamsDone(event!!)

    }

    fun getHostFromAuthenticatedUser() : ArrayList<String>
    {
        val host = ArrayList<String>()
        // Si le token à bien été stocké on connecte l'utilisateur à l'activité principale
        val hostUser : AuthUser? = SecureStorageServices.authUser
        if (hostUser != null)
            host.add(hostUser.id)

        return host
    }

    fun useDatePicker() {

        // Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener{
                view, mYear, mMonth, mDay ->
                event_creation_date.setText(""+adapteDaysNumber(mDay)+"/"+adapteDaysNumber(mMonth)+"/"+mYear)
        }, year, month, day)
        dpd.show()
    }

    fun setOnEventParamsListener(callback: OnEventParamsListener) {
        this.listener = callback
    }

    fun adapteDaysNumber(day : Int) : String {
        if (day <= 9)
            return "0"+day
        else
            return ""+day
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnEventParamsListener {
        fun onParamsDone(event: SendableEvent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EventParamsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(event: SendableEvent) =
            EventParamsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, event)
                }
            }
    }
}
