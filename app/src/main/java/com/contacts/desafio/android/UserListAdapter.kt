package com.contacts.desafio.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.list.desafio.android.R
import com.list.desafio.android.databinding.ListItemUserBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users = emptyList<User>()
        set(value) {
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userItem = ListItemUserBinding.bind(itemView)

        fun bind(user: User) {
            userItem.name.text = user.name
            userItem.username.text = user.username
            userItem.progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(user.img)
                .error(R.drawable.ic_round_account_circle)
                .into(userItem.picture, object : Callback {
                    override fun onSuccess() {
                        userItem.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        userItem.progressBar.visibility = View.GONE
                    }
                })
        }
    }
}