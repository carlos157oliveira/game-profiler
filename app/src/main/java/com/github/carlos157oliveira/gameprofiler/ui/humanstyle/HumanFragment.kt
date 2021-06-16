package com.github.carlos157oliveira.gameprofiler.ui.humanstyle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.carlos157oliveira.gameprofiler.R
import com.github.carlos157oliveira.gameprofiler.ui.ProfileResultActivity
import com.github.carlos157oliveira.gameprofiler.utils.Profile

class HumanFragment : Fragment() {

    private lateinit var homeViewModel: HumanViewModel
    private lateinit var btnObtainResult: Button
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioGroupMood: RadioGroup

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HumanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_human_style, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.btnObtainResult = view.findViewById(R.id.btnObtainResult)
        this.radioGroupGender = view.findViewById(R.id.radioGroupGender)
        this.radioGroupMood = view.findViewById(R.id.radioGroupMood)

        this.btnObtainResult.setOnClickListener {

            var gender: String? = null
            when(this.radioGroupGender.checkedRadioButtonId) {
                R.id.radioMan -> gender = "male"
                R.id.radioWoman -> gender = "female"
            }

            var mood: String? = null
            when(this.radioGroupMood.checkedRadioButtonId) {
                R.id.radioHappy -> mood = "happy"
                R.id.radioSad -> mood = "sad"
                R.id.radioSurprised -> mood = "surprised"
            }

            val params = mapOf("mood" to mood)

            val intent = Intent(this.activity, ProfileResultActivity::class.java)
            intent.putExtra("sprite", gender ?: "female")
            intent.putExtra("queryString", Profile.getQueryString(params))
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}