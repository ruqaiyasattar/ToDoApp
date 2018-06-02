package app.bootcamp.com.todoapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabsAdapter(fm:FragmentManager,val tabs:Array<TasksList>) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment = tabs[position]

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? = tabs[position].title

}