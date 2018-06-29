package vn.tiki.android.androidhometestclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import vn.tiki.android.androidhometestclone.adapter.DealAdapter
import vn.tiki.android.androidhometestclone.data.api.ApiServices
import vn.tiki.android.androidhometestclone.data.api.response.Deal
import vn.tiki.android.androidhometestclone.di.initDependencies
import vn.tiki.android.androidhometestclone.di.inject
import vn.tiki.android.androidhometestclone.di.releaseDependencies
import vn.tiki.android.androidhometestclone.listener.ITimeOutListener
import vn.tiki.android.androidhometestclone.presenter.DealCountDownContract
import vn.tiki.android.androidhometestclone.presenter.DealCountDownPresenter

class MainActivity : AppCompatActivity(), DealCountDownContract.View, ITimeOutListener {

    override fun onTimeOut(deal: Deal) {
        rcvDeals.postDelayed({
            dealAdapter.removeTimeOutDeal(deal)
        }, 300)//prevent crash : Cannot call this method while RecyclerView is computing a layout or scrolling
    }

    private val apiServices by inject<ApiServices>()
    private lateinit var dealAdapter: DealAdapter
    private lateinit var presenter: DealCountDownPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependencies(this)
        presenter = DealCountDownPresenter(apiServices, this)
        setContentView(R.layout.activity_main)

        dealAdapter = DealAdapter(ArrayList(), this)
        dealAdapter.listener = this
        val layoutManager = GridLayoutManager(this, DealAdapter.COLUMN_COUNT)
        rcvDeals.layoutManager = layoutManager
        rcvDeals.adapter = dealAdapter
        presenter.getDeals()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseDependencies()
    }

    override fun showLoadingDialog() {
        pbLoading?.visibility = View.VISIBLE
    }

    override fun hideLoadingDialog() {
        pbLoading?.visibility = View.GONE
    }

    override fun showDeals(deals: List<Deal>?) {
        dealAdapter.setDeals(deals)
    }
}
