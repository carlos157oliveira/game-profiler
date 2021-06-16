package com.github.carlos157oliveira.gameprofiler.ui.micahstyle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.carlos157oliveira.gameprofiler.R
import com.github.carlos157oliveira.gameprofiler.ui.ProfileResultActivity
import com.github.carlos157oliveira.gameprofiler.utils.Profile


class MicahFragment : Fragment() {

    private lateinit var slideshowViewModel: MicahViewModel

    private lateinit var btnObtainResult: Button
    private lateinit var spinners: Array<Spinner>
    private lateinit var labels: Array<String>
    private lateinit var resources: Array<Int>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(MicahViewModel::class.java)
        return inflater.inflate(R.layout.fragment_micah_style, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.spinners = arrayOf(
            view.findViewById(R.id.spEarrings),
            view.findViewById(R.id.spEarringColor),
            view.findViewById(R.id.spEyebrows),
            view.findViewById(R.id.spEyebrowColor),
            view.findViewById(R.id.spEyes),
            view.findViewById(R.id.spEyeColor),
            view.findViewById(R.id.spFacialHair),
            view.findViewById(R.id.spFacialHairColor),
            view.findViewById(R.id.spMouth),
            view.findViewById(R.id.spNose)
        )
        this.labels = arrayOf(
			"earrings",
			"earringColor",
			"eyebrows",
			"eyebrowColor",
			"eyes",
			"eyeColor",
			"facialHair",
			"facialHairColor",
			"mouth",
			"nose"
        )
        this.resources = arrayOf(
			R.array.earrings,
			R.array.colors,
			R.array.eyebrows,
			R.array.colors,
			R.array.eyes,
			R.array.colors,
			R.array.facialHair,
			R.array.colors,
			R.array.mouth,
			R.array.nose
        )

        for (i in this.spinners.indices) {
            val adapter = ArrayAdapter.createFromResource(view.context, this.resources[i], android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinners[i].adapter = adapter
        }

        this.btnObtainResult = view.findViewById(R.id.btnObtainResult)
        this.btnObtainResult.setOnClickListener {

            val params = mutableMapOf<String, String?>()

            for (i in this.spinners.indices) {
                var item = spinners[i].selectedItem as String?
                item = if (item == "(nulo)") null else item
                val label = labels[i]
                params[label] = item
                if ((label == "earrings" || label == "facialHair" )
                        && item != null) {
                    params[label + "Probability"] = "100"
                }
            }

            val intent = Intent(this.activity, ProfileResultActivity::class.java)
            intent.putExtra("sprite", "micah")
            intent.putExtra("queryString", Profile.getQueryString(params))
            startActivity(intent)
        }
        
        super.onViewCreated(view, savedInstanceState)
    }
}