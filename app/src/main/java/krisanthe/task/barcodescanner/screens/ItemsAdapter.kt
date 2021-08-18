package krisanthe.task.barcodescanner.screens

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import krisanthe.task.barcodescanner.R
import krisanthe.task.barcodescanner.common.AdapterData
import krisanthe.task.barcodescanner.common.BaseViewHolder
import krisanthe.task.barcodescanner.common.UDate
import krisanthe.task.barcodescanner.model.Item

class ItemsAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    val items: ArrayList<AdapterData<Any>> = ArrayList()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    fun update(
        firstTwoRecords: List<Item>? = emptyList(),
        lastTwoRecords: List<Item>? = emptyList(),
    ) {
        items.clear()

        if (firstTwoRecords?.isNotEmpty() == true) {
            items += AdapterData(RecordViewHolder.viewType, firstTwoRecords)
        } else {
            items += AdapterData(
                EmptyViewHolder.viewType,
                "Nie zeskanowano żadnych kodów kreskowych"
            )
            notifyDataSetChanged()
            return
        }
        items += AdapterData(SpaceViewHolder.viewType, Unit)

        if (lastTwoRecords?.isNotEmpty() == true) {
            items += AdapterData(RecordViewHolder.viewType, lastTwoRecords)
        } else {
            items += AdapterData(
                EmptyViewHolder.viewType,
                "Nie zeskanowano wystarczającą ilość kodów kreskowych"
            )
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            RecordViewHolder.viewType -> RecordViewHolder(parent)
            EmptyViewHolder.viewType -> EmptyViewHolder(parent)
            else -> SpaceViewHolder.createSpace(parent.context)
        }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as? BaseViewHolder<Any>)?.bind(items[position])
    }

    class RecordViewHolder(parent: ViewGroup) : BaseViewHolder<AdapterData<List<Item>>>(
        LayoutInflater.from(parent.context).inflate(R.layout.record_item_layout, parent, false)
    ) {
        private val firstItem: View = itemView.findViewById(R.id.first_item)
        private val secondItem: View = itemView.findViewById(R.id.second_item)

        companion object {
            const val viewType = 1
        }

        override fun bind(itemData: AdapterData<List<Item>>) {
            itemData.data.forEachIndexed { index, item ->
                getView(index)?.apply {
                    findViewById<TextView>(R.id.code).apply {
                        text = item.code
                    }
                    findViewById<TextView>(R.id.timestamp).apply {
                        text = UDate.getDate(item.timestamp)
                    }
                    findViewById<TextView>(R.id.name).apply {
                        text = item.name
                    }
                }
            }
        }

        private fun getView(index: Int): View? =
            when (index) {
                0 -> firstItem
                1 -> secondItem
                else -> null
            }
    }

    class EmptyViewHolder(parent: ViewGroup) : BaseViewHolder<AdapterData<String>>(
        LayoutInflater.from(parent.context).inflate(R.layout.empty_item_layout, parent, false)
    ) {

        private val textView = itemView as? TextView

        companion object {
            const val viewType = 2
        }

        override fun bind(itemData: AdapterData<String>) {
            textView?.text = itemData.data
        }
    }

    class SpaceViewHolder(viewItem: View) : BaseViewHolder<AdapterData<Unit>>(viewItem) {

        companion object {
            const val viewType = 3

            fun createSpace(context: Context): SpaceViewHolder {
                val view = View(context).apply {
                    val params =
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100)
                    layoutParams = params
                }
                return SpaceViewHolder(view)
            }
        }

        override fun bind(itemData: AdapterData<Unit>) {}
    }
}