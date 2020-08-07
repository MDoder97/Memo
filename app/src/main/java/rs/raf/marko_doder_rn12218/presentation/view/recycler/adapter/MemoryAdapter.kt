package rs.raf.marko_doder_rn12218.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.view.recycler.diff.MemoryDiffCallback
import rs.raf.marko_doder_rn12218.presentation.view.recycler.viewHolder.MemoryViewHolder

class MemoryAdapter(
    private val onClicked: (Memory) -> Unit
) : ListAdapter<Memory, MemoryViewHolder>(MemoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_memory, parent, false)
        return MemoryViewHolder(
            view
        ) { onClicked.invoke(getItem(it)) }
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}