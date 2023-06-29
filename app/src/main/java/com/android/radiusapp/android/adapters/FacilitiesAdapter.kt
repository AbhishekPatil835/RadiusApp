package com.android.radiusapp.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.radiusapp.R
import com.android.radiusapp.android.model.Exclusion
import com.android.radiusapp.android.model.Facility
import com.android.radiusapp.android.model.Option

class FacilitiesAdapter(
    private val facilities: ArrayList<Facility>,
    private val exclusionList: ArrayList<ArrayList<Exclusion>>
) : RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>() {

    private val selectedOptions = mutableMapOf<String, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_facilities, parent, false)
        return FacilityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        val facility = facilities[position]
        holder.bind(facility)
    }

    override fun getItemCount(): Int = facilities.size

    inner class FacilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val facilityName: TextView = itemView.findViewById(R.id.facility_name)
        private val optionsRecyclerView: RecyclerView =
            itemView.findViewById(R.id.options_recycler_view)


        init {
            optionsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)

        }

        fun bind(facility: Facility) {
            facilityName.text = facility.name
            optionsRecyclerView.adapter = OptionsAdapter(facility.options, facility.facilityId)

        }
    }

    inner class OptionsAdapter(
        private val options: ArrayList<Option>,
        private val facilityId: String
    ) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_options, parent, false)
            return OptionViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
            val option = options[position]
            holder.bind(option)
        }

        override fun getItemCount(): Int = options.size

        inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val optionIcon: ImageView = itemView.findViewById(R.id.option_icon)
            private val optionName: TextView = itemView.findViewById(R.id.option_name)
            private val optionCheckbox: CheckBox = itemView.findViewById(R.id.option_checkbox)
            fun bind(option: Option) {
                optionIcon.setImageResource(getIconResourceId(option.icon))
                optionName.text = option.name
                optionCheckbox.isChecked = option.id == selectedOptions[facilityId]

                optionCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedOptions[facilityId] = option.id
                        handleExclusions()
                    }
                    else {
                        selectedOptions.remove(facilityId)
                    }
                }
            }

        }
    }

    private fun getIconResourceId(iconName: String): Int {
        // Map icon names to corresponding resource IDs
        // You can replace this with your own icon mapping logic
        return when (iconName) {
            "rooms" -> R.drawable.rooms
            "no-room" -> R.drawable.no_room
            "swimming" -> R.drawable.swimming
            "garden" -> R.drawable.garden
            "garage" -> R.drawable.garage
            "apartment" -> R.drawable.apartment
            "condo" -> R.drawable.condo
            "boat" -> R.drawable.boat
            else -> R.drawable.land
        }
    }

    private fun handleExclusions() {
        for (exclusion in exclusionList) {
            val facilityId1 = exclusion[0].facilityId
            val optionsId1 = exclusion[0].optionsId
            val facilityId2 = exclusion[1].facilityId
            val optionsId2 = exclusion[1].optionsId

            if (selectedOptions.containsKey(facilityId1) && selectedOptions.containsKey(facilityId2)) {
                val selectedOptionId1 = selectedOptions[facilityId1]
                val selectedOptionId2 = selectedOptions[facilityId2]

                if (selectedOptionId1 == optionsId1 && selectedOptionId2 == optionsId2) {
                    selectedOptions.remove(facilityId2)
                    notifyItemChanged(facilities.indexOfFirst { it.facilityId == facilityId2 })
                }
                else if (selectedOptionId1 == optionsId2 && selectedOptionId2 == optionsId1) {
                    selectedOptions.remove(facilityId1)
                    notifyItemChanged(facilities.indexOfFirst { it.facilityId == facilityId1 })
                }
            }
        }
    }

    fun getSelectedOptions(): Map<String, String> = selectedOptions
}
