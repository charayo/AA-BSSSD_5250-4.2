package com.example.aa_fileio
import org.json.JSONObject
import java.util.*

class Note(var name:String, var desc:String, var date:String?) {
    init {
        if (date == null){
            date = Date().toString()
        }
    }
    fun toJSON(): JSONObject {
        //make a new json object
        val jsonObject = JSONObject().apply {
            //put each place of data into the object
            put("name", name)
            put("date", date)
            put("desc", desc)
        }
        return jsonObject
    }
    override fun toString(): String {
        return "$name, $date, $desc"
    }
}