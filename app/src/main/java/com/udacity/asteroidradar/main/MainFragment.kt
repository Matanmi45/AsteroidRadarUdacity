package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.Repository.AsteroidRadarRepository
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

     private val viewModel: MainViewModel by viewModels {
         MainViewModelFactory(
             requireActivity().application, AsteroidRadarRepository(getDatabase(requireContext()))
         )
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMainBinding.inflate(inflater)



        binding.lifecycleOwner = this



        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidRadarAdapter(AsteroidRadarAdapter.OnClickListener{
            viewModel.displayAsteroidDetails(it)
        } )

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer{
            if (it != null) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }

        } )

        setHasOptionsMenu(true)

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.show_a_week_Asteroid -> viewModel.getAWeekAsteroid()
            R.id.show_a_day_Asteroid -> viewModel.getADayAsteroid()
            R.id.show_saved_Asteroid -> viewModel.getAllAsteroid()
        }

        return true
    }
}
