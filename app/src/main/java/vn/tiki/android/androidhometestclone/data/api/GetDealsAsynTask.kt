package vn.tiki.android.androidhometestclone.data.api

import android.os.AsyncTask
import vn.tiki.android.androidhometestclone.data.api.response.Deal
import vn.tiki.android.androidhometestclone.presenter.DealCountDownContract

class GetDealsAsyncTask(private val apiServices: ApiServices, private val callback: DealCountDownContract.View) : AsyncTask<Unit, Unit, List<Deal>>() {

    override fun onPreExecute() {
        super.onPreExecute()
        callback.showLoadingDialog()
    }

    override fun doInBackground(vararg params: Unit?): List<Deal> {
        return apiServices.getDeals()
    }

    override fun onPostExecute(result: List<Deal>?) {
        super.onPostExecute(result)
        callback.showDeals(result)
        callback.hideLoadingDialog()
    }
}