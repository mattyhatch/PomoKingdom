package com.example.pomokingdom

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pomokingdom.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    lateinit var timerStudy: CountDownTimer
    lateinit var timerShort: CountDownTimer
    lateinit var timerLong: CountDownTimer
    var timerOn = false
    var timerState = 0
    var counter = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?):View? {
        super.onCreateView(inflater,container,savedInstanceState)
        binding = FragmentHomeBinding.inflate(inflater)
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
        return view
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