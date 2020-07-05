package com.sibin.embibeassignment.base.exception

sealed class Success {
    object StorageSuccess : Success()
}