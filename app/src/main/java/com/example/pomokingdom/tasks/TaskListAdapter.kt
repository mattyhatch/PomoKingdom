package com.example.pomokingdom.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.example.pomokingdom.ApiClasses.AddTaskResult
import com.example.pomokingdom.ApiClasses.DeleteTask
import com.example.pomokingdom.ApiClasses.User
import com.example.pomokingdom.HomeActivity
import com.example.pomokingdom.R
import com.example.pomokingdom.UserViewModel
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class TaskListAdapter(
    private var taskList:MutableList<String>? = null,
    private val listener:OnItemClickListener,
    private val user:User? = null
): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.task_list_item,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList!![position])
    }

    override fun getItemCount():Int {
        return if (taskList == null) 0 else taskList!!.size
    }

    fun setList(list:MutableList<String>?) {
        taskList = list
        notifyDataSetChanged()
    }
    fun addToList(task:String) {
        taskList?.add(task)
        notifyDataSetChanged()
    }
    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private var taskName = itemView.findViewById<TextView>(R.id.task_item)
        private var remove = itemView.findViewById<Button>(R.id.removeTask)

        init {
            itemView.setOnClickListener(this)
            remove.setOnClickListener {
                deleteTask(this)
            }
        }

        private fun deleteTask(holder: TaskViewHolder) {
            val position = holder.adapterPosition
            val retrofit = RetrofitClient.getInstance()
            val myService = retrofit.create(MyService::class.java)
            val str = taskList?.get(position)
            val str1 = user?.oldUser?.getId()
            myService.apiDeleteTask(DeleteTask(str1, str)).enqueue(
                object:
                    Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(response.code() != 200) {
                            Toast.makeText(itemView.context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                            return
                        }
                        val gson = Gson()
                        val str = response.body()
                        val result = gson.fromJson(str, AddTaskResult::class.java)
                        if(result.status == "success") {
                            taskList?.remove(taskList?.get(position))
                            setList(taskList)
                            Toast.makeText(itemView.context,"Successfully deleted", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(itemView.context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(itemView.context,"Failed to connect", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }


        override fun onClick(v: View?) {
            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(pos)
            }
        }

        fun bind(task:String) {
            //Still to implement with api call
            bindOrHide(taskName,task)
        }

        fun bindOrHide(textView: TextView, data:String?) {
            if(data == null) {
                textView.visibility = View.GONE
            } else {
                textView.text = data
                textView.visibility = View.VISIBLE
            }
        }
    }
}
interface OnItemClickListener {
    fun onItemClick(position:Int)
}