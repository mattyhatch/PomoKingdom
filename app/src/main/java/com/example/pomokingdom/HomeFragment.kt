package com.example.pomokingdom

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pomokingdom.ApiClasses.*
import com.example.pomokingdom.databinding.FragmentHomeBinding
import com.example.pomokingdom.retrofit.MyService
import com.example.pomokingdom.retrofit.RetrofitClient
import com.example.pomokingdom.tasks.OnItemClickListener
import com.example.pomokingdom.tasks.TaskListAdapter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home),OnItemClickListener {
    lateinit var binding: FragmentHomeBinding
    lateinit var timerStudy: CountDownTimer
    lateinit var timerShort: CountDownTimer
    lateinit var timerLong: CountDownTimer
    lateinit var myService: MyService
    private var user:User? = null
    var timerOn = false
    var timerState = 0
    var counter = 0
    private val model:UserViewModel by activityViewModels()

    private lateinit var recyclerView:RecyclerView
    private var taskList =mutableListOf<String>()
    private lateinit var adapter1:TaskListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?):View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater)
        val retrofit = RetrofitClient.getInstance()
        myService = retrofit.create(MyService::class.java)
        binding.start.setOnClickListener {
            if(!timerOn) {
                timerOn = true
                binding.start.text = getString(R.string.stop)
                startTimer()
            }
            else {
                timerOn = false
                binding.start.text = getString(R.string.start)
                stopTimer()
            }
        }
        val view = binding.root
        recyclerView = binding.taskView
        binding.addTask.setOnClickListener {addTask()}
        val gson  = Gson()
        user = model?.user
        myService.apiGetTasks(user?.oldUser?.getId()).enqueue(
            object:
                Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.code() != 200) {
                        Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    val str  = response.body()
                    val gson = Gson()
                    val result = gson.fromJson(str,TaskList::class.java)
                    if(result.tasks != null) {
                        for (tasks in result.tasks!!) {
                            taskList.add(tasks.name)
                        }
                    }
                    adapter1 = TaskListAdapter(taskList,this@HomeFragment,user)
                    setUpRecyclerView()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context,"Failed to connect,TaskList will be empty", Toast.LENGTH_SHORT).show()
                    adapter1 = TaskListAdapter(taskList,this@HomeFragment,user)
                    setUpRecyclerView()
                }
            }
        )
        return view
    }

    override fun onItemClick(position: Int) {
        val str = taskList[position]
        binding.currentTask.text = str
    }

    private fun addTask() {
        val taskStr = binding.editTask.text.toString()
        myService.apiAddTask(AddTask(user?.oldUser?.getId(),taskStr)).enqueue(object:
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.code() != 200) {
                    val lmao = response.errorBody()
                    Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                    return
                }
                val gson = Gson()
                val str = response.body()
                val result = gson.fromJson(str, AddTaskResult::class.java)
                if(result.status == "success") {
                    binding.editTask.setText("Add Task", TextView.BufferType.EDITABLE)
                    adapter1.addToList(taskStr)
                    Toast.makeText(context,"Successfully added task", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context,"Response failed"+response.errorBody(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context,"Failed to connect", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpRecyclerView() {
        binding.taskView.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }
        val animator = binding.taskView.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    fun startTimer() {
        var studyT = binding.editStudy.text.toString().toInt() * 60000
        var shortB = binding.editStudy.text.toString().toInt() * 60000
        var longB = binding.editLong.text.toString().toInt() * 60000
        timerStudy = object: CountDownTimer(studyT.toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                var timeM = millisUntilFinished/60000
                var timeS = (millisUntilFinished/1000)%60
                if(timeS < 10){
                    binding.timer.text = "$timeM:0$timeS"
                }else {
                    binding.timer.text = "$timeM:$timeS"
                }
            }

            override fun onFinish() {
                if(counter == 4){
                    counter = 0
                    timerState = 2
                    binding.phase.text = getString(R.string.long_break)
                    timerLong.start()
                }else {
                    counter++
                    timerState = 1
                    binding.phase.text = getString(R.string.short_break)
                    timerShort.start()
                }
            }
        }
        timerShort = object: CountDownTimer(shortB.toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                var timeM = millisUntilFinished/60000
                var timeS = (millisUntilFinished/1000)%60
                if(timeS < 10){
                    binding.timer.text = "$timeM:0$timeS"
                }else {
                    binding.timer.text = "$timeM:$timeS"
                }
            }

            override fun onFinish() {
                timerState = 0
                binding.phase.text = getString(R.string.study)
                timerStudy.start()
            }
        }
        timerLong = object: CountDownTimer(longB.toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                var timeM = millisUntilFinished/60000
                var timeS = (millisUntilFinished/1000)%60
                if(timeS < 10){
                    binding.timer.text = "$timeM:0$timeS"
                }else {
                    binding.timer.text = "$timeM:$timeS"
                }
            }

            override fun onFinish() {
                stopTimer()
            }
        }
        timerStudy.start()
    }

    fun stopTimer() {
        if(timerState == 0) {
            timerStudy.cancel()
        } else if(timerState == 1) {
            timerShort.cancel()
        } else if(timerState == 2) {
            timerLong.cancel()
        }
        binding.timer.text = "00:00"
        binding.phase.text = getString(R.string.study)
    }
}