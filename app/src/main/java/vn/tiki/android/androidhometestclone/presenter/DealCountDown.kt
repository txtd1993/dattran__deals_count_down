package vn.tiki.android.androidhometestclone.presenter

import vn.tiki.android.androidhometestclone.data.api.response.Deal

interface DealCountDownContract {
    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showDeals(deals: List<Deal>?)
    }

    interface Presenter {
        fun getDeals()
    }
}