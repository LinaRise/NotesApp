package com.nikak.linadom.notesapp

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.nikak.linadom.notesapp.DbManager
import com.nikak.linadom.notesapp.R
import kotlinx.android.synthetic.main.activity_add_notes.*
class AddNotesActivity : AppCompatActivity() {
    val dbTable="Notes"
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)




        try{
            var bundle:Bundle= intent.extras!!
            id=bundle.getInt("id",0)
            if(id!=0) {
                editTitle.setText(bundle.getString("title") )
                editDes.setText(bundle.getString("description") )

            }
        }catch (ex:Exception){}


    }

    fun  add(view:View){
        var dbManager= DbManager(this)

        var values= ContentValues()
        values.put("title",editTitle.text.toString())
        values.put("description",editDes.text.toString())


        if(id==0) {
            val ID = dbManager.insert(values)
            if (ID > 0) {
                Toast.makeText(this, " note is added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " cannot add note ", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArs= arrayOf(id.toString())
            val ID = dbManager.update(values,"id=?",selectionArs)
            if (ID > 0) {
                Toast.makeText(this, " note is added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, " cannot add note ", Toast.LENGTH_LONG).show()
            }
        }

    }
}

