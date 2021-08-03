package com.myfittinglife.fuzzysearchproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author LD
 * @Time 2021/7/29 11:57
 * @Describe
 * @Modify
 */
class MyAdapter(private val data:MutableList<String>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    lateinit var  myClickListener:MyClickListener


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvContent.text = data[position]
        holder.itemView.setOnClickListener {
            //直接实现
//            val intent = Intent(it.context,NewsShowActivity::class.java)
//            val url = Const.serchUrl+data[position]+"&cl=3"
//            intent.putExtra("url",url)
//            it.context.startActivity(intent)

            //接口方式实现
            myClickListener.onClick(position)
        }
    }

    fun setOnMyClickListener(listener: MyClickListener){
        this.myClickListener = listener
    }
    interface MyClickListener{
        fun onClick(position: Int)
    }
}