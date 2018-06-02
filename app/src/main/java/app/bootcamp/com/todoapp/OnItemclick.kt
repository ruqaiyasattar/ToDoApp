package app.bootcamp.com.todoapp

import android.view.View

interface OnItemclick {
    fun onItemClick(view: View, position: Int)
}