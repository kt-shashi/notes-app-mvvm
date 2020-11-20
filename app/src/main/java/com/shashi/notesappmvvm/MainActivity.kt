package com.shashi.notesappmvvm

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INoteAdapterListner {

    lateinit var viewModel: NoteViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var noteList: ArrayList<Note>
    lateinit var noteAdapter: NoteAdapter
    lateinit var buttonSave: Button
    lateinit var editTextNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, { list ->

            list?.let {
                noteAdapter.updateNotes(list as ArrayList<Note>)
            }

        })
    }

    private fun initViews() {
        editTextNote = findViewById(R.id.edit_text_note)
        buttonSave = findViewById(R.id.button_save)
        recyclerView = findViewById(R.id.recycler_view_note)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteList = ArrayList()
        noteAdapter = NoteAdapter(this, this)
        recyclerView.adapter = noteAdapter

        buttonSave.setOnClickListener {
            insertNote()
        }
    }

    private fun insertNote() {
        val noteInput: String = editTextNote.text.toString().trim()
        if (noteInput.isEmpty()) {
            editTextNote.error = "Cannot be empty"
            return
        }

        viewModel.insertNote(Note(noteInput))
    }

    override fun onItemClick(note: Note) {
        viewModel.deleteNote(note)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val menuId = item.itemId

        if (menuId == R.id.menu_item_delete) {
            deleteAllData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        viewModel.deleteAllNotes()
    }
}