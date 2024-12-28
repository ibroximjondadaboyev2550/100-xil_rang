package com.example.a100xilrang

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a100xilrang.adapters.ColorAdapter
import com.example.a100xilrang.adapters.ScaleItemAnimator
import com.example.a100xilrang.adapters.ScaleItemDecoration
import com.example.a100xilrang.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        Thread {
            val uniqueColors = generateUniqueColors(2000000)
            runOnUiThread {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                recyclerView.layoutManager = GridLayoutManager(this, 2)
                recyclerView.adapter = ColorAdapter(uniqueColors)
                recyclerView.addItemDecoration(ScaleItemDecoration(1.2f))
                recyclerView.itemAnimator = ScaleItemAnimator()
            }
        }.start()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrollY = recyclerView.computeVerticalScrollOffset()
                val alpha = (scrollY / 1000f).coerceAtMost(1f)
                recyclerView.setBackgroundColor(Color.argb((255 * alpha).toInt(), 255, 255, 255))
            }
        })
    }

    private fun generateUniqueColors(size: Int): List<Int> {
        val uniqueColors = mutableSetOf<Int>()
        while (uniqueColors.size < size) {
            val randomColor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            uniqueColors.add(randomColor)
        }
        return uniqueColors.toList()
    }
}

