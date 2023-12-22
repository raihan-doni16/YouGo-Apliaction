package com.example.yougoapp.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.yougoapp.R
import com.example.yougoapp.adapter.RecArtAdapter
import com.example.yougoapp.adapter.RecPosAdapter
import com.example.yougoapp.data.State
import com.example.yougoapp.databinding.FragmentHomeBinding
import com.example.yougoapp.factory.ViewModelFactory
import com.example.yougoapp.ui.article.ArticleActivity
import com.example.yougoapp.ui.pose.PoseFragment


class HomeFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RecPosAdapter
    private lateinit var articleAdapter: RecArtAdapter
    private val viewModel by viewModels<HomeViewModel> {

        ViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        val imageSlider: ImageSlider? = binding.imageYougo
        binding.seeArticle.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
            true
        }
        progressBar = binding.spinKit
        progressBar.isVisible = true
        binding.seePose.setOnClickListener {

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = PoseFragment()
            fragmentTransaction.replace(R.id.navHost, newFragment)
            fragmentTransaction.commit()
        }


        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.poseYogaRecyclerView.layoutManager = layoutManager

        val layout = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.articleKebugaranRecyclerView.layoutManager = layout

        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                R.drawable.home_yoga,
                "Yoga: Your path to inner strength and tranquility."
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.yoga1,
                "Embrace the journey of self-discovery through yoga"
            )
        )
        imageList.add(
            SlideModel(
                R.drawable.yoga2,
                "Cultivate wellness and mindfulness through the art of yoga"
            )
        )
        imageSlider?.setImageList(imageList, ScaleTypes.CENTER_CROP)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPose().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is State.Loading -> {
                        progressBar.isVisible = false
                    }

                    is State.Success -> {
                        val pose = data.data
                        adapter = RecPosAdapter(pose)
                        binding.poseYogaRecyclerView.adapter = adapter
                    }

                    is State.Error -> {

                    }

                    else -> false
                }
            }
        }
        viewModel.getArticle().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                when (data) {
                    is State.Loading -> {

                    }

                    is State.Success -> {
                        val pose = data.data
                        articleAdapter = RecArtAdapter(pose)
                        binding.articleKebugaranRecyclerView.adapter = articleAdapter
                    }

                    is State.Error -> {

                    }

                    else -> false
                }
            }
        }
    }
}