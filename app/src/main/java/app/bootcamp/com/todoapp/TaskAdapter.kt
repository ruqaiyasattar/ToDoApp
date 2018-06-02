package app.bootcamp.com.todoapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.taskitem.view.*

class TaskAdapter(val items : ArrayList<TaskToDo>,val onItemCompleteClick:(Int,TaskToDo)->Unit,val onItemDelClick:(Int,TaskToDo)->Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    data class TaskToDo(val task:String,var isComplete: Boolean = false, val key:String = ""){
        constructor() : this("")

        override fun equals(other: Any?): Boolean = other != null && other is TaskToDo && other.key == key
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvAnimalType = view.text
        val comtem=view.complete
        var delItem =view.deletitem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.taskitem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = items[position];
        holder?.tvAnimalType?.text = task.task
        holder?.comtem.setOnClickListener({
           // it.textyet.setText(holder?.d.text)
            onItemCompleteClick(position,task);

        })
        holder.delItem.setOnClickListener {
            onItemDelClick(position,task);
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }
}

