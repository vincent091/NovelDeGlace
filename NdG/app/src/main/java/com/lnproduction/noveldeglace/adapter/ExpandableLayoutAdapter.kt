package com.lnproduction.noveldeglace.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.model.Chapters
import com.lnproduction.noveldeglace.model.Tome
import java.util.*




class ExpandableLayoutAdapter(private val dataSet: ArrayList<Tome>, private val backgroundColor: Int, private val textColor: Int, private val listener : ContentListener) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return dataSet.get(groupPosition)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val tome = getGroup(groupPosition) as Tome
        var view = convertView
        if (view == null) {
            val layoutInflater = parent?.context
                    ?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.item_header, parent,false)
        }
        val expandedListTextView = view?.findViewById(R.id.item_header_name) as TextView
        val groupLayout : LinearLayout = view.findViewById(R.id.group_layout)
        val arrowIMg : ImageView = view.findViewById(R.id.item_arrow)
        groupLayout.setBackgroundColor(backgroundColor)
        expandedListTextView.setTextColor(textColor)
        expandedListTextView.text = "Tome "+ tome.tomeId
        if(isExpanded)
        {
            arrowIMg.rotation = 180F
        }
        else
        {
            arrowIMg.rotation = 0F
        }
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return dataSet.get(groupPosition).chapterList.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return dataSet.get(groupPosition).chapterList.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val chapter = getChild(groupPosition,childPosition) as Chapters
        var view = convertView
        if (view == null) {
            val layoutInflater = parent?.context
                    ?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.item_detail, parent,false)
        }
        val expandedListTextView = view?.findViewById(R.id.item_name) as TextView
        val childLayout : LinearLayout = view.findViewById(R.id.child_layout)
        childLayout.setBackgroundColor(backgroundColor)
        expandedListTextView.text = chapter.chapterName
        expandedListTextView.setTextColor(textColor)
        view.setOnClickListener { listener.onChapters(chapter.chapterUrl) }
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return dataSet.size
    }

    interface ContentListener {
        fun onChapters(url: String)
    }
}