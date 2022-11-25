package com.example.wakelockjavaapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class BlankFragment : Fragment() {

    val myViewmodel : Myviewmodel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textview = view.findViewById<TextView>(R.id.textviewFragment)


        Log.d("divnfvfvf",myViewmodel.toString())

        lifecycleScope.launchWhenResumed{
            myViewmodel.stateFlow.collect {
// textview.text = it
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            myViewmodel.sharedFlow.collect{
                textview.text = it
            }
        }

    }

}