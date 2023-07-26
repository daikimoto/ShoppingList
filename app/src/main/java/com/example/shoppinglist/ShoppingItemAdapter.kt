package com.example.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingItemAdapter: RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>() {

    var shoppingItemList = listOf<String>()
    var checkedItems = listOf<Boolean>()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.shopping_item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingItemAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_shopping, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShoppingItemAdapter.ViewHolder, position: Int) {
        val item = shoppingItemList[position]
        viewHolder.textView.text = item.toString()
    }

    // 購入品リストのサイズ（要素数）取得
    override fun getItemCount() = shoppingItemList.size

    // 購入品リストにアイテムを追加する
    fun addList(item: String) {
        shoppingItemList += listOf(item)
    }

    // チェック済みのアイテムを削除
    fun deleteCheckedItem(){

    }
}