package com.android.radiusapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.radiusapp.android.adapters.FacilitiesAdapter
import com.android.radiusapp.android.usecases.RadiusResult
import com.android.radiusapp.android.viewmodels.RadiusViewModel
import com.android.radiusapp.base_framework.network.Response
import com.android.radiusapp.databinding.ActivityMainBinding
import com.android.radiusapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private fun <T : ViewBinding> initBinding(binding: T): View {
        return with(binding) {
            root
        }
    }

    private val viewModel by viewModels<RadiusViewModel>()
    private lateinit var facilitiesRecyclerView: RecyclerView
    lateinit var facilitiesAdapter: FacilitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initBinding(binding))
        observeViewModels()
        setupViews()
        viewModel.getRadiusData()
    }

    private fun setupViews() {
        facilitiesRecyclerView = binding.facilitiesRecyclerView
        facilitiesRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun observeViewModels() {
        viewModel.facilitiesLiveData
            .observe(this) { result ->
                when (result.status) {
                    Response.Status.LOADING -> {

                    }
                    Response.Status.SUCCESS -> {
                        when (result.data) {
                            is RadiusResult.RadiusSuccess -> {
                                val facilitiesData = (result.data).facilitiesResponseModel

                                facilitiesAdapter = FacilitiesAdapter(facilitiesData.facilities, facilitiesData.exclusions)
                                facilitiesRecyclerView.adapter = facilitiesAdapter

                            }

                            is RadiusResult.RadiusFailure -> {

                                Toast.makeText(
                                    this,
                                    (result.data).errorMessage, Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {

                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    Response.Status.ERROR -> {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
}