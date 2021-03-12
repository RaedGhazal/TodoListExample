package com.raedghazal.todolistexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raedghazal.todolistexample.adapters.TodoAdapter
import com.raedghazal.todolistexample.databinding.ActivityMainBinding
import com.raedghazal.todolistexample.model.Todo
import com.raedghazal.todolistexample.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


        viewModel.init()

        //getting data from viewModel
        viewModel.getTodosList().observe(this, {
            todoAdapter.todos = it
            Log.d(TAG, "init: ${it.size}")
        })

        //showing/hiding progress bar if data is updating
        viewModel.getIsUpdating().observe(this, {

            binding.apply {
                progressBar.visibility =
                    if (it)
                        View.VISIBLE
                    else
                        View.GONE
            }
        })
        setupRecyclerView()

    }

    private fun setupRecyclerView() = binding.apply {
        todoAdapter = TodoAdapter()
        rvTodos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }
}