package de.remotelight.mobile.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.remotelight.mobile.R

class EffectRecyclerViewAdapter(private var list: List<String> = emptyList()): RecyclerView.Adapter<EffectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EffectViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EffectViewHolder, position: Int) {
        val name: String = list[position]
        holder.bind(name)
    }

    override fun getItemCount(): Int = list.size

    fun getList() = list

    fun setList(list: List<String>) {
        this.list = list
        notifyDataSetChanged()
    }

}

class EffectViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.rv_effect_item, parent, false)) {

    private var effectName: TextView? = null

    init {
        effectName = itemView.findViewById(R.id.tvEffectName)
    }

    fun bind(name: String) {
        effectName?.text = name
    }

}