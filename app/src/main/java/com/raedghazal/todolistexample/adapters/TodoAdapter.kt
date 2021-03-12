package com.raedghazal.todolistexample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raedghazal.todolistexample.model.Todo
import com.raedghazal.todolistexample.databinding.ItemTodoBinding

class TodoAdapter() :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    /** DiffUtils
     * replaces notifyDataSetChanged() and refresh the changed item only not the whole recycler view
     * */

    private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Todo>?
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {
            todos?.let {
                val todo = it[position]
                tvTitle.text = todo.title
                cbDone.isChecked = todo.completed
            } ?: run { root.visibility = View.GONE }
        }

    }

    override fun getItemCount() = todos?.size ?: 0
}