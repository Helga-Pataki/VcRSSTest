package com.example.vcrsstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vcrsstest.contentViewModel.ContentItemViewModel
import com.example.vcrsstest.contentViewModel.MFactory
import com.example.vcrsstest.databinding.FragmentContentBinding


class VsContentFragment : Fragment() {

    var urlContent: String? = null
    private var mBinding: FragmentContentBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        mBinding = FragmentContentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments

        urlContent = bundle?.getString("link")

        binding.txtTitle.text = bundle?.getString("title")
        binding.txtPubdate.text = bundle?.getString("pubDate")

        binding.arrow.setOnClickListener {
            activity?.onBackPressed()
        }
        val imag = bundle?.getString("imag")
        if (imag?.isEmpty() != true) {
            context?.let {
                Glide.with(it)
                    .load(bundle?.getString("imag"))
                    .into(binding.featuredImg)
            }
        }
//        else {
//            binding.featuredImg.setImageResource(R.drawable.ic_vc_8)
//        }
        val modelC =
            ViewModelProvider(this, MFactory(urlContent!!)).get(ContentItemViewModel::class.java)
        modelC.loadContent().observe(viewLifecycleOwner, {
            binding.txtContent.text = it
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
