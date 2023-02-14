package com.example.aa_fileio

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONArray
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var notesData:NotesData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        makeData()
        readDataAsJSON()
    }

    override fun onResume() {
        super.onResume()
        val jsonArray = readDataAsJSON()
        if (jsonArray != null){
            Log.d("MACT", "Data found")
            loadJSONNotes(jsonArray)
        }else {
            Log.d("MACT", "Data not found!")
            makeData()
        }
    }

    override fun onPause() {
        super.onPause()
        writeDataToFile(notesData)
    }
    private fun makeData(){
        notesData = NotesData(applicationContext)
        for (i in 1..5) {
            val todo = Note("Note $i", "For the birthday party.", null)
            notesData.addNote(todo)
        }
    }

    private fun writeDataToFile(notesData: NotesData){
        val file:String = "notes.json"
        val data:String = notesData.toJSON().toString()
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun readDataAsJSON():JSONArray?{
        try {
            val fileInputStream: FileInputStream? = openFileInput("notes.json")
            val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text)
            }
            fileInputStream?.close()
            return JSONArray(stringBuilder.toString())
        } catch(e: FileNotFoundException){
            return null
        }

    }
    private fun loadJSONNotes(data:JSONArray){
        val notesData2 = NotesData(applicationContext).apply {
            loadNotes(data)
        }
        Log.d("MainActivity result", notesData2.toString())
    }

}