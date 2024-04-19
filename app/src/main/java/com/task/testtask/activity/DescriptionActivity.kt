package com.task.testtask.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.testtask.R
import com.task.testtask.databinding.ActivityDescriptionBinding

@Suppress("DEPRECATION")
class DescriptionActivity : AppCompatActivity() {

    private val TAG: String = "DetailsActivity"
    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvTitle = intent.getStringExtra("tvTitle")
        val tvBody = intent.getStringExtra("tvBody")

        binding.TvTitle.text = tvTitle
        binding.TVDescriptionData.text = tvBody

        binding.IvBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}