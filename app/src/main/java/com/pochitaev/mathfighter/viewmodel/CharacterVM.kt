package com.pochitaev.mathfighter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pochitaev.mathfighter.data.entity.ShopEntity
import com.pochitaev.mathfighter.data.repository.ShopRepo

class CharacterVM(private var shopRepo: ShopRepo) : ViewModel() {

    var allprices: List<ShopEntity> = shopRepo.getCategories()

    fun update(item: ShopEntity) {
        shopRepo.updatePrice(item)
    }
}