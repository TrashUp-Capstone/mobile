package com.dicoding.trashup.ui.user.profile.faq

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.entity.Faq
import com.dicoding.trashup.databinding.ActivityUserFaqBinding
import java.util.Locale

class UserFaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserFaqBinding
    private lateinit var adapter: UserFaqAdapter
    private var faqList = ArrayList<Faq>()
    private var originalFaqList = ArrayList<Faq>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addDataToList()
        setupRecyclerView()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Faq>()
            for (i in faqList) {
                if (i.question.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.submitList(filteredList)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = UserFaqAdapter()
        binding.rvFaq.setHasFixedSize(true)
        binding.rvFaq.layoutManager = LinearLayoutManager(this)
        binding.rvFaq.adapter = adapter
        adapter.submitList(faqList)
    }

    private fun addDataToList() {
        faqList.add(
            Faq(
                getString(R.string.faq_q1),
                getString(R.string.faq_a1),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q2),
                getString(R.string.faq_a2),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q3),
                getString(R.string.faq_a3),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q4),
                getString(R.string.faq_a4),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q5),
                getString(R.string.faq_a5),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q6),
                getString(R.string.faq_a6),
            )
        )
        faqList.add(
            Faq(
                getString(R.string.faq_q7),
                getString(R.string.faq_a7),
            )
        )

    }
}