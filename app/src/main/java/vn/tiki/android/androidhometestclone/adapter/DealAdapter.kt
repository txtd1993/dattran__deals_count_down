package vn.tiki.android.androidhometestclone.adapter

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.deal_item.view.*
import vn.tiki.android.androidhometestclone.R
import vn.tiki.android.androidhometestclone.data.api.response.Deal
import vn.tiki.android.androidhometestclone.listener.ITimeOutListener
import vn.tiki.android.androidhometestclone.util.UtilsHelper
import java.util.*


class DealAdapter(private val items: ArrayList<Deal>, private val context: Context) : RecyclerView.Adapter<DealAdapter.DealHolderView>() {
    lateinit var listener: ITimeOutListener

    companion object {
        const val ITEMS_SPACING = 15
        const val COLUMN_COUNT = 2
    }

    fun setDeals(deals: List<Deal>?) {
        deals?.let {
            items.addAll(deals)
            notifyDataSetChanged()
        }
    }

    fun removeTimeOutDeal(deal: Deal) {
        items.remove(deal)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealHolderView {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.deal_item, parent, false)
        val holder = DealHolderView(view)
        val ivPicWidth = UtilsHelper.getWidthOfScreen(parent.context as Activity) / COLUMN_COUNT - ITEMS_SPACING
        holder.ivDealPic.layoutParams = FrameLayout.LayoutParams(ivPicWidth, ivPicWidth)
        holder.listener = this.listener

        return holder
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DealHolderView, position: Int) {
        holder.bindViewData(context, items[position])
    }


    class DealHolderView(view: View) : RecyclerView.ViewHolder(view) {
        var listener: ITimeOutListener? = null

        var ivDealPic = view.iv_deal_picture as ImageView
        var tvCountDown = view.tv_deal_count_down as TextView
        var tvDealName = view.tv_deal_name as TextView
        var tvDealPrice = view.tv_deal_price as TextView
        private var timer: CountDownTimer? = null

        fun bindViewData(context: Context, deal: Deal) {
            timer?.cancel()

            ivDealPic.setImageBitmap(UtilsHelper.getBitmapFromAssets(deal.productThumbnail, context))
            tvDealName.text = deal.productName
            val priceStr = deal.productPrice.toString() + " $"
            tvDealPrice.text = priceStr
            val deltaTime = UtilsHelper.getDeltaTimeByMilliSeconds(deal.endDate)
            if (deltaTime > 0) {
                tvCountDown.text = deltaTime.toString()

                timer = object : CountDownTimer(deltaTime, 1000) {
                    override fun onFinish() {
                        tvCountDown.text = "Time Out"
                        listener?.onTimeOut(deal)
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        val timeLeft = (millisUntilFinished / 1000).toString() + " Sec"
                        tvCountDown.text = timeLeft
                    }
                }.start()
            } else {
                tvCountDown.text = "Time Out"
                listener?.onTimeOut(deal)
            }
        }
    }
}