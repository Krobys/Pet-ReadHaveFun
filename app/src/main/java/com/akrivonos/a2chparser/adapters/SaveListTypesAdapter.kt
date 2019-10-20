package com.akrivonos.a2chparser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.viewholders.TypeSaveViewHolder
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage
import com.akrivonos.a2chparser.models.SaveTypeModel

class SaveListTypesAdapter(context: Context?, private val detailedPageListener: OpenDetailedSavePage) : RecyclerView.Adapter<TypeSaveViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var saveTypes: ArrayList<SaveTypeModel>

    fun setSaveTypeList(saveTypeList: ArrayList<SaveTypeModel>) {
        saveTypes = ArrayList(saveTypeList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeSaveViewHolder {
        val view: View = layoutInflater.inflate(R.layout.adapteritem_savetype_types, parent, false)

        return TypeSaveViewHolder(view, detailedPageListener)
    }

    override fun onBindViewHolder(holder: TypeSaveViewHolder, position: Int) {
        val saveTypeModel = saveTypes[position]
        holder.typeModel = saveTypeModel
        holder.nameTypeSaveTextView.text = saveTypeModel.nameSave
    }

    override fun getItemCount(): Int = saveTypes.size

    companion object {
        const val SAVE_TYPE_BOARD = 1
        const val SAVE_TYPE_THREAD = 2
        const val SAVE_TYPE_COMMENT = 3
        const val SAVE_TYPE_MEDIA = 4
        const val SAVE_TYPE_NOVALUE = 0
    }

}