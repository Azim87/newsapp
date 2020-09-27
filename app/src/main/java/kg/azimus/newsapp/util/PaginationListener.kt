package kg.azimus.newsapp.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationListener(linearLayoutManager: LinearLayoutManager?) : RecyclerView.OnScrollListener() {
    var layoutManager: LinearLayoutManager? = null

    init {
        layoutManager = linearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager!!.itemCount
        val firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >=
                totalItemCount && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }


    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}