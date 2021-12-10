package project.stn991524577_991473606.tszKinNikita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import project.stn991524577_991473606.tszKinNikita.models.Workout
import project.stn991524577_991473606.tszKinNikita.databinding.ListRecordBinding

class WorkoutListAdapter(private val onItemClicked: (Workout) -> Unit): ListAdapter<Workout, WorkoutListAdapter.WorkoutListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder {
        return WorkoutListViewHolder(
            ListRecordBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class WorkoutListViewHolder(private var binding: ListRecordBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Workout) {
            binding.sportDate.text = item.date
            binding.sportName.text = item.getSportName()
        }
    }

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem.getSportName() == newItem.getSportName()
            }

        }
    }

}