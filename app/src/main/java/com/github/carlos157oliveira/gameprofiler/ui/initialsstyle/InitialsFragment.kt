package com.github.carlos157oliveira.gameprofiler.ui.initialsstyle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.carlos157oliveira.gameprofiler.R
import com.github.carlos157oliveira.gameprofiler.ui.ProfileResultActivity
import com.github.carlos157oliveira.gameprofiler.utils.Profile

class InitialsFragment : Fragment() {

    private lateinit var initialsViewModel: InitialsViewModel
    private lateinit var txtInitials: EditText
    private lateinit var btnObtainResult: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        initialsViewModel =
                ViewModelProvider(this).get(InitialsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_initials_style, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.txtInitials = view.findViewById(R.id.txtInitials)
        this.btnObtainResult = view.findViewById(R.id.btnObtainResult)
        this.btnObtainResult.setOnClickListener {
            val intent = Intent(this.activity, ProfileResultActivity::class.java)
            intent.putExtra("sprite", "initials")
            Toast.makeText(view.context, "oi" + "fs" + this.txtInitials.text, Toast.LENGTH_LONG)
            intent.putExtra("queryString", this.txtInitials.text.toString())
            startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}