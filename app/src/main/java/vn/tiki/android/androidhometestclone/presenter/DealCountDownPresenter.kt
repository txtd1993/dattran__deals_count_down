package vn.tiki.android.androidhometestclone.presenter

import vn.tiki.android.androidhometestclone.data.api.ApiServices
import vn.tiki.android.androidhometestclone.data.api.GetDealsAsyncTask

class DealCountDownPresenter(private val apiService: ApiServices,
                             private val view: DealCountDownContract.View) : DealCountDownContract.Presenter {
    override fun getDeals() {
        val getDealsAsyncTask = GetDealsAsyncTask(apiService, view)
        getDealsAsyncTask.execute()
    }
}