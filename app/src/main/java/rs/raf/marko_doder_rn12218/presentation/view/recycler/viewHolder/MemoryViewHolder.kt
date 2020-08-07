package rs.raf.marko_doder_rn12218.presentation.view.recycler.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_item_memory.*
import kotlinx.android.synthetic.main.layout_item_memory.view.*
import rs.raf.marko_doder_rn12218.data.models.Memory

class MemoryViewHolder(
    override val containerView: View,
    private val onClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    init {
        containerView.memoryItem.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onClicked.invoke(adapterPosition)
            }
        }
    }

    fun bind(memory: Memory) {
        titleTv.text = memory.title
        contentTv.text = memory.content
        dateTv.text = memory.date
    }

}