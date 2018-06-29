package vn.tiki.android.androidhometestclone.listener

import vn.tiki.android.androidhometestclone.data.api.response.Deal

interface ITimeOutListener {
    fun onTimeOut(deal: Deal)
}