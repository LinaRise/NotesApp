package com.nikak.linadom.notesapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.ticket.view.*


class ProfileActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show()

        loadQuery("%")


    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }


    fun loadQuery(title: String) {


        val dbManager = DbManager(this)
        val projections = arrayOf("id", "title", "description")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.query(projections, "title like ?", selectionArgs, "title")
        listNotes.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("id"))
                val Title = cursor.getString(cursor.getColumnIndex("title"))
                val Description = cursor.getString(cursor.getColumnIndex("description"))

                listNotes.add(Note(ID, Title, Description))

            } while (cursor.moveToNext())
        }

        var myNotesAdapter = MyNotesAdpater(this, listNotes)
        notesListView.adapter = myNotesAdapter


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val sv: SearchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            when (item.itemId) {
                R.id.addNote -> {
                    //Got to add paage
                    val intent = Intent(this, AddNotesActivity::class.java)
                    startActivity(intent)
                }
                R.id.exit -> {
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }


    inner class MyNotesAdpater(context: Context, var listNotesAdpater: ArrayList<Note>) : BaseAdapter() {
        override fun getItem(position: Int): Any {
            return listNotesAdpater[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdpater.size
        }

        var context: Context? = context

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val myView = layoutInflater.inflate(R.layout.ticket, null)

            val myNote = listNotesAdpater[p0]
            myView.titleTv.text = myNote.noteName
            myView.contentTv.text = myNote.noteDes
            myView.deleteBtn.setOnClickListener {
                val dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myNote.noteID.toString())
                dbManager.delete("id=?", selectionArgs)
                loadQuery("%")
            }
            myView.editBtn.setOnClickListener {

                goToUpdate(myNote)

            }
            return myView
        }


    }


    fun goToUpdate(note: Note) {
        val intent = Intent(this, AddNotesActivity::class.java)
        intent.putExtra("id", note.noteID)
        intent.putExtra("title", note.noteName)
        intent.putExtra("description", note.noteDes)
        startActivity(intent)
    }
}


