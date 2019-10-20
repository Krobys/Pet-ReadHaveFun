package com.akrivonos.a2chparser.models

class SaveTypeModel(private val nameSave: String, private val typeSaveItem: Int) {
    fun getName(): String = nameSave
    fun getItemType(): Int = typeSaveItem
}