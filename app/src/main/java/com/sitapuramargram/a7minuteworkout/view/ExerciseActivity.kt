package com.sitapuramargram.a7minuteworkout.view

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sitapuramargram.a7minuteworkout.Constants
import com.sitapuramargram.a7minuteworkout.R
import com.sitapuramargram.a7minuteworkout.adapter.ExerciseStatusAdapter
import com.sitapuramargram.a7minuteworkout.databinding.ActivityExerciseBinding
import com.sitapuramargram.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding
import com.sitapuramargram.a7minuteworkout.model.ExerciseModel
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var exerciseBinding: ActivityExerciseBinding
    private var restTimer: CountDownTimer?=null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30
    private var resetTimerDuration: Long = 10
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech?= null
    private var player:MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exerciseBinding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(exerciseBinding.root)

        setSupportActionBar(exerciseBinding.toolbar)
        val actionBar = supportActionBar
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)

        }

        exerciseBinding.toolbar.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        setUpExerciseStatusRecyclerview()




    }

    override fun onBackPressed() {
        customDialogForBackButton()

    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        dialogBinding.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }


    private fun setRestProgressBar(){

        exerciseBinding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(resetTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                exerciseBinding.progressBar.max = resetTimerDuration.toInt()
                exerciseBinding.progressBar.progress = resetTimerDuration.toInt()-restProgress
                exerciseBinding.tvTimmer.text = (resetTimerDuration.toInt()-restProgress).toString()

            }

            override fun onFinish() {

                //Toast.makeText(this@ExerciseActivity,"Here now we will start the exercise",Toast.LENGTH_LONG).show()
                currentExercisePosition++
                exerciseList!![currentExercisePosition].isSelected= true
                exerciseAdapter!!.notifyDataSetChanged()
               setupExerciseView()


            }
        }.start()



    }

    private fun setExerciseProgressBar(){

        exerciseBinding.pbExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                exerciseBinding.pbExercise.max = exerciseTimerDuration.toInt()
                exerciseBinding.pbExercise.progress = exerciseTimerDuration.toInt()-exerciseProgress
                exerciseBinding.tvExerciseTimmer.text = (exerciseTimerDuration.toInt()-exerciseProgress).toString()

            }

            override fun onFinish() {

                //Toast.makeText(this@ExerciseActivity,"Here we will start the next rest screen",Toast.LENGTH_LONG).show()
                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].isSelected = false
                    exerciseList!![currentExercisePosition].isCompleted = true
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{
                    startActivity(Intent(this@ExerciseActivity,FinishActivity::class.java))
                    finish()
                   // Toast.makeText(this@ExerciseActivity,"Congratulations! You have completed the 7 minutes workout.",Toast.LENGTH_LONG).show()
                }


            }
        }.start()



    }

    private fun setupRestView(){

        try {
            //val soundURI= Uri.parse("android:resource://com.sitapuramargram.a7minuteworkout/"+R.raw.press_start)
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e:Exception){

            e.printStackTrace()
        }


        exerciseBinding.llRestView.visibility = View.VISIBLE
        exerciseBinding.llExerciseView.visibility = View.GONE
        exerciseBinding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition+1].name

        if(restTimer !=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()


    }

    private fun setupExerciseView(){

        exerciseBinding.llExerciseView.visibility = View.VISIBLE
        exerciseBinding.llRestView.visibility = View.GONE
        exerciseBinding.ivImage.setImageResource(exerciseList!![currentExercisePosition].image)
        exerciseBinding.tvExerciseName.text =  exerciseList!![currentExercisePosition].name

        if(exerciseTimer !=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].name)
        setExerciseProgressBar()



    }

    override fun onDestroy() {

        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress =0
        }

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress =0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }

        super.onDestroy()

    }

    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            val result= tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.d("TTS","The language specified is not  supported!")
            }
        }else{
            Log.d("TTS","Initialization Failed!")
        }
    }

    private fun speakOut(text: String){

        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")


    }

    private fun setUpExerciseStatusRecyclerview(){
        exerciseBinding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        exerciseBinding.recyclerview.adapter = exerciseAdapter
    }
}