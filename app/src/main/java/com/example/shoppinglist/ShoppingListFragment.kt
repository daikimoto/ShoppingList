package com.example.shoppinglist

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MediatorLiveData
import com.example.shoppinglist.databinding.FragmentShoppingListBinding
import com.google.android.material.resources.MaterialResources.getDrawable

class ShoppingListFragment: Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentShoppingListBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        // RecyclerViewの取得
        val recyclerView = binding.shoppingRvList
        // LayoutManagerの取得
        val layoutManager = LinearLayoutManager(view.context)
        // RecyclerAdapterの取得
        val adapter = ShoppingItemAdapter()
        // 購入品リストのEditTextの取得
        val editText: EditText = binding.addShoppingList.addEditText
        // 購入品リストのButtonの取得
        val addListButton: ImageButton = binding.addShoppingList.addButton

        // layoutManagerとadapterをRecyclerViewにセット
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        /*
        val dividerItemDecoration = DividerItemDecoration(view.context, LinearLayoutManager(view.context).orientation)
        getDrawable(R.drawable.divider)?.let { dividerItemDecoration.setDrawable(it) }
        recyclerView.addItemDecoration(dividerItemDecoration)
        */

        // 購入品リスト追加ボタンのClickイベント
        //editText.setOnEditorActionListener { textView, i, keyEvent ->  }
        addListButton.setOnClickListener {
            val addTextItem: String = editText.text.toString()
            adapter.addList(addTextItem)
            editText.editableText.clear()
            adapter.notifyItemInserted(adapter.shoppingItemList.lastIndex)
        }

        // 購入品リスト追加ボタンの活性／非活性状態
        addListButton.isClickable = false
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                // 追加購入品入力欄のテキスト有無によって活性／非活性の状態を遷移
                addListButton.isClickable = editText.text.isNotEmpty()
            }
        })

        // Enterキー押下時のイベントハンドラ
        editText.setOnKeyListener {
            view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

        return  view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Enterキー押下でキーボードを閉じる
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}