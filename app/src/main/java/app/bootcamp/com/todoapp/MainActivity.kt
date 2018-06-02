package app.bootcamp.com.todoapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import app.bootcamp.com.todoapp.TaskAdapter.TaskToDo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {


    val auth  = FirebaseAuth.getInstance();

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("tasks").child(auth.currentUser?.uid ?: intent.getStringExtra("imei"))
    val todoRef = myRef.orderByChild("complete").equalTo(false)
    val completedRef = myRef.orderByChild("complete").equalTo(true)

    val tasks: ArrayList<TaskToDo> = ArrayList()
    val adapterTasks = TaskAdapter(tasks,onItemCompleteClick = { position, task ->
        task.isComplete = true
        myRef.child(task.key).setValue(task)
    }, onItemDelClick = { position, task ->
        myRef.child(task.key).removeValue()
    })

    val tasksCompleted: ArrayList<TaskToDo> = ArrayList()
    val adapterCompletedTasks = TaskAdapter(tasksCompleted,onItemCompleteClick = { position, task ->
        task.isComplete = false
        myRef.child(task.key).setValue(task)
    }, onItemDelClick = { position, task ->
        myRef.child(task.key).removeValue()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val task = TasksList()
        task.title = "Not Yet"
        task.adapter = adapterTasks

        val completedTask = TasksList()
        completedTask.title = "Completed"
        completedTask.adapter = adapterCompletedTasks

        val tabs:Array<TasksList> = arrayOf(task,completedTask)

        tabsPager.adapter = TabsAdapter(supportFragmentManager,tabs)
        tabsLayout.setupWithViewPager(tabsPager)

        todoRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                if(dataSnapshot != null){
                    val task:TaskToDo? = dataSnapshot.getValue(TaskToDo::class.javaObjectType)
                    if(task !=null){
                        tasks.add(task).also {
                            adapterTasks.notifyItemInserted(tasks.size-1)
                        }
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                if(dataSnapshot != null){
                    val task:TaskToDo? = dataSnapshot.getValue(TaskToDo::class.javaObjectType)
                    if(task !=null){
                        tasks.remove(task).also {
                            adapterTasks.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
        completedRef.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                if(dataSnapshot != null){
                    val task:TaskToDo? = dataSnapshot.getValue(TaskToDo::class.javaObjectType)
                    if(task !=null){
                        tasksCompleted.add(task).also {
                            adapterCompletedTasks.notifyItemInserted(tasksCompleted.size-1)
                        }
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                if(dataSnapshot != null){
                    val task:TaskToDo? = dataSnapshot.getValue(TaskToDo::class.javaObjectType)
                    if(task !=null){
                        tasksCompleted.remove(task).also {
                            adapterCompletedTasks.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

        fab.setOnClickListener { view ->
            val key = myRef.push().key
            myRef.child(key).setValue(TaskToDo(editText3.text.toString(),key=key));
            editText3.setText("")
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



}
