package org.codejudge.application.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.codejudge.application.databinding.ItemRestaurantBinding
import org.codejudge.application.domain.model.RestaurantListResModel
import org.codejudge.application.utils.loadImage

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.RepoViewHolder>() {

    private val dataList = arrayListOf<RestaurantListResModel>()

    fun setListData(listResModel: List<RestaurantListResModel>){
        dataList?.clear()
        dataList?.addAll(listResModel)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewHolder: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(viewHolder.context),
                viewHolder,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {
       viewHolder.bind(dataList[position])
    }

    override fun getItemCount( ) = dataList.size


    class RepoViewHolder(private val item: ItemRestaurantBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(data: RestaurantListResModel) {
            item.apply {
                tvRestaurantName.text = data.restaurantName
                tvRating.text = "${data.restaurantRating}"
                ivRestaurantImage.loadImage(data.restaurantImageParams)
                if (data.isOpened== true) {
                    tvRestaurantStatus.text = "Open Now"
                    tvRestaurantStatus.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_green_light))
                }else{
                    tvRestaurantStatus.text = "Closed"
                    tvRestaurantStatus.setTextColor(ContextCompat.getColor(itemView.context,android.R.color.holo_orange_light))
                }
            }
        }
    }
}