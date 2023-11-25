package com.example.dalportal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobListing(
    val id: String,
    val title: String,
    // ... other fields ...
) : Parcelable
