package com.wsr.practice_activityresultapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    private lateinit var observer: ImageSetter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = requireActivity().findViewById<ImageView>(R.id.image_view)
        val button = requireActivity().findViewById<Button>(R.id.button)
        button.setOnClickListener {
            observer.selectImage()
        }

        observer = ImageSetter(requireActivity(), imageView)
        lifecycle.addObserver(observer)
    }
}
