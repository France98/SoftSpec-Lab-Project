package com.example.france98.finalproject.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.france98.finalproject.Interface.ItemClickListener
import com.example.france98.finalproject.Model.RSSObject

import kotlinx.android.synthetic.main.row.view.*
import org.w3c.dom.Text

class FeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener, View.OnLongClickListener
{

    private var itemClickListener : ItemClickListener?=null

    init {

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v,adapterPosition,false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v,adapterPosition,true)
        return true
    }

}

class FeedAdapter(private val rssObject: RSSObject, private val mContexT:Context): RecyclerView.Adapter<FeedViewHolder>() {

    private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(mContexT)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.itemView.textTitle.text = rssObject.items[position].title
//        holder.itemView.textContent.text = rssObject.items[position].content
        holder.itemView.textContent.setText(Html.fromHtml(rssObject.items[position].content))
        holder.itemView.textPubdate.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener{view ,position, isLongClick ->
            if(!isLongClick){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContexT.startActivity(browserIntent)
            }
        })
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(com.example.france98.finalproject.R.layout.row,parent,false)
        return FeedViewHolder(itemView)
    }

}