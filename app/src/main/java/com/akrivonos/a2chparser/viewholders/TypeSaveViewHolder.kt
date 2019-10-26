package com.akrivonos.a2chparser.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage
import com.akrivonos.a2chparser.models.SaveTypeModel

class TypeSaveViewHolder(itemView: View, detailedSavePage: OpenDetailedSavePage) : RecyclerView.ViewHolder(itemView) {

    val nameTypeSaveTextView: TextView = itemView.findViewById(R.id.name_savetype)
    lateinit var typeModel: SaveTypeModel

    init {
        itemView.setOnClickListener {
            detailedSavePage.openSavePage(typeModel)
        }
    }
}