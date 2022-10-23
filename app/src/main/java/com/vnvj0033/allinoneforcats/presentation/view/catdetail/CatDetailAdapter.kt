package com.vnvj0033.allinoneforcats.presentation.view.catdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vnvj0033.allinoneforcats.R
import com.vnvj0033.allinoneforcats.databinding.ContentCatListItemBinding
import com.vnvj0033.allinoneforcats.domain.model.Cat

class CatDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var viewEvent: CatDetailView.AdapterEvent? = null
        fun setCatDetailView(event: CatDetailView.AdapterEvent) { viewEvent = event }

    private val item = ArrayList<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.content_cat_list_item, parent, false) as ContentCatListItemBinding
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cat = item[position]
        if (holder is CatViewHolder) {
            holder.bind(cat)
        }
    }

    override fun getItemCount(): Int = item.size

    fun addData(list: List<Cat>) {
        item.addAll(list)
        notifyItemRangeInserted(itemCount - list.size, list.size)
    }

    inner class CatViewHolder(private val binding: ContentCatListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            binding.imageviewCatCatDetail.setOnClickListener {
                viewEvent?.goToCatDetail(cat)
            }
        }

    }
}