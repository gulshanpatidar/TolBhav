package com.example.tolbhav.tol_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TolViewModel @Inject constructor(): ContainerHost<TolState,TolSideEffect>,ViewModel(){

    override val container: Container<TolState, TolSideEffect>
     = container(
         initialState = TolState()
     )

    fun updateCurrentWeight(
        weight: String
    ){
        val weightInDouble = weight.toDoubleOrNull()
        if (weightInDouble != null){
            blockingIntent {
                reduce {
                    state.copy(
                        currentWeight = weightInDouble
                    )
                }
            }
        }
    }

    fun onClickAddWeight(){
        if (container.stateFlow.value.currentWeight == 0.0){
            return
        }
        //verify the weight and add it to the list while deleting the current variable
        val newList = container.stateFlow.value.weightEntries.toMutableList()
        newList.add(container.stateFlow.value.currentWeight)
        var totalAmount = 0.0
        var totalWeight = 0.0
        val price = container.stateFlow.value.price
        newList.forEach {
            totalAmount += it.times(price)
            totalWeight += it
        }
        intent {
            reduce {
                state.copy(
                    weightEntries = newList,
                    totalAmount = totalAmount,
                    currentWeight = 0.0,
                    totalWeight = totalWeight
                )
            }
        }
    }

    fun updatePrice(price: String){
        //verify the price and update it while updating the total amount
        val priceInDouble = price.toDoubleOrNull()
        if (priceInDouble != null){
            var totalAmount = 0.0
            container.stateFlow.value.weightEntries.forEach {
                totalAmount += it.times(priceInDouble)
            }
            blockingIntent {
                reduce {
                    state.copy(
                        price = priceInDouble,
                        totalAmount = totalAmount
                    )
                }
            }
        }
    }


}