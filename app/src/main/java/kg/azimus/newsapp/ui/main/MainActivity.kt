package kg.azimus.newsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.softrunapps.paginatedrecyclerview.PaginatedAdapter
import kg.azimus.newsapp.R
import kg.azimus.newsapp.model.Articles
import kg.azimus.newsapp.model.News
import kg.azimus.newsapp.ui.NewsDetailsActivity
import kg.azimus.newsapp.ui.main.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NewsAdapter
    private val mMainViewModel by viewModel<MainViewModel>()
    private val mList = ArrayList<Articles>()
    private var swipeContainer: SwipeRefreshLayout? = null
    private var counter = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeContainer = findViewById(R.id.swipe_layout)
        mRecyclerView = findViewById(R.id.main_recycler)
        initView()
        updateDate()
    }

    private fun updateDate() {
        if (mList != null) {
            fetchLocalData()
        } else {
            fetchNetworkData()
        }
    }


    private fun fetchNetworkData() {
        mMainViewModel.successState.observe(this, { dataSucces ->
            mList.addAll(dataSucces.articles)
            saveToLocalDataBase(dataSucces)
        })
        mMainViewModel.getNewsData()
    }

    private fun fetchLocalData() {
        Log.d(TAG, "fetchLocalData: ")
        mMainViewModel.localNewsList.observe(this, { localData ->
            mList.addAll(localData.articles)
        })
        mMainViewModel.readFromLocalDataBase()
    }

    private fun initView() {
        swipeContainer!!.setOnRefreshListener(this)
        mAdapter = NewsAdapter(this::onItemPositionClick)
        mAdapter.setDefaultRecyclerView(this, R.id.main_recycler)
        mAdapter.setOnPaginationListener(object : PaginatedAdapter.OnPaginationListener {

            override fun onCurrentPage(page: Int) {
                Toast.makeText(this@MainActivity, "Page $page loaded!", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }

            override fun onNextPage(page: Int) {
                loadNextPage(page)
                showLoading(true)
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "finish", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

        loadNextPage(mAdapter.startPage)
    }

    private fun loadNextPage(page: Int) {
        Log.d(TAG, "loadNextPage: ")
        val timer = object : CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                showLoading(false)
                val users: ArrayList<Articles> = ArrayList()

                val start = page * counter - counter
                val end = page * counter

                for (i in start until end) {
                    if (i < mList.size) {
                        users.add(mList[i])
                    }
                }
                onGetDate(users)
            }
        }
        timer.start()
    }

    fun onGetDate(users: ArrayList<Articles>) {
        mAdapter.submitItems(users)
    }

    private fun saveToLocalDataBase(news: News) {
        mMainViewModel.saveToLocalData(news)
    }

    private fun onItemPositionClick(articles: Articles) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        intent.putExtra("author", articles.author)
        intent.putExtra("source", articles.source?.name)
        intent.putExtra("content", articles.content)
        intent.putExtra("description", articles.description)
        intent.putExtra("publishedAt", articles.publishedAt)
        intent.putExtra("title", articles.title)
        intent.putExtra("urlToImage", articles.urlToImage)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        progress_bar.visibility = if (isLoading) View.VISIBLE else
            View.GONE
    }

    override fun onRefresh() {
        val timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                updateDate()
                swipeContainer?.isRefreshing = false
            }
        }
        timer.start()
    }
}

