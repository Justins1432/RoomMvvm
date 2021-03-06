package com.example.rommmvvm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.rommmvvm.R
import com.example.rommmvvm.model.Author

class AuthorAdapter(
    val context: Context,
    val authorClickDelete: AuthorClickDeleteInterface,
    val authorClick: AuthorClickInterface
) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {
    private var authorlist = ArrayList<Author>()

    inner class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surnameAuthor: TextView = itemView.findViewById<TextView>(R.id.txtSurname)
        val nameAuthor: TextView = itemView.findViewById<TextView>(R.id.txtName)
        val patronymicAuthor: TextView = itemView.findViewById<TextView>(R.id.txtPatronymic)
        val deleteAuthor: ImageView = itemView.findViewById<ImageView>(R.id.dltEntry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.author_item, parent, false)
        return AuthorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.surnameAuthor.text = authorlist[position].surname
        holder.nameAuthor.text = authorlist[position].name
        holder.patronymicAuthor.text = authorlist[position].patronymic

        holder.deleteAuthor.setOnClickListener {
            authorClickDelete.onDeleteIconClick(authorlist[position])
        }

        holder.itemView.setOnClickListener {
            authorClick.onNoteClick(authorlist[position])
        }
    }

    override fun getItemCount(): Int {
        return authorlist.size
    }

    fun updateList(updList: List<Author>) {
        authorlist.clear()
        authorlist.addAll(updList)
        notifyDataSetChanged()
    }

    fun swap(newList: List<Author>){
        val diffUtilCallback = AuthorsDiffUtilCallback(authorlist, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        authorlist.clear()
        authorlist.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}

interface AuthorClickDeleteInterface {
    fun onDeleteIconClick(author: Author)
}

interface AuthorClickInterface {
    fun onNoteClick(author: Author)
}

