package rs.raf.marko_doder_rn12218.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.marko_doder_rn12218.data.models.Memory

class MemoryDiffCallback : DiffUtil.ItemCallback<Memory>() {

    override fun areItemsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.content == newItem.content &&
                oldItem.date == newItem.date
    }

}