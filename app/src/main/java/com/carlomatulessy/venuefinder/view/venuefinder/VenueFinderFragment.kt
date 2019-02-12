package com.carlomatulessy.venuefinder.view.venuefinder

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.carlomatulessy.venuefinder.viewmodel.VenueFinderViewModel
import kotlinx.android.synthetic.main.venue_finder_fragment.*


class VenueFinderFragment : Fragment() {

    companion object {
        fun newInstance() = VenueFinderFragment()
    }

    private lateinit var viewModel: VenueFinderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.carlomatulessy.venuefinder.R.layout.venue_finder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VenueFinderViewModel::class.java)

        viewModel.setResultsObserver(this,
            Observer { data ->
                context?.let { safeContext ->
                    venueResultList.adapter = VenueFinderAdapter(safeContext, data)
                }
            },
            Observer {
                setVisibility(it)
            })

        venueSearchField.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    requestResultsFromInput(text.toString())
                    closeKeyboard()
                    true
                } else
                    false
            }
        }
    }

    private fun requestResultsFromInput(value: String) {
        viewModel.getResultsFromValue(this, value)
    }

    private fun closeKeyboard() {
        activity?.let {
            venueSearchField.clearFocus()
            (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(venueSearchField.windowToken, 0)
        }
    }

    // TODO binding
    private fun setVisibility(value: Boolean) {
        if (value) {
            venueResultList.visibility = View.VISIBLE
            venueEmptyView.visibility = View.GONE
        } else {
            venueResultList.visibility = View.GONE
            venueEmptyView.visibility = View.VISIBLE
        }
    }

}
