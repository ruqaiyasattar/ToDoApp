package app.bootcamp.com.todoapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*

class TasksList : Fragment() {

    var title:String = "Fragment Title"
    lateinit var adapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val page = inflater.inflate(R.layout.fragment_tasks, container, false)
        page.findViewById<RecyclerView>(R.id.todoList).layoutManager = LinearLayoutManager(context)
        page.findViewById<RecyclerView>(R.id.todoList).adapter = adapter
        return page
    }


}
