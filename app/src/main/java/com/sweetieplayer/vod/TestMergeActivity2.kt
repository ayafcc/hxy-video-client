package com.sweetieplayer.vod

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jaygoo.library.m3u8downloader.M3U8Library
import jaygoo.library.m3u8downloader.service.VideoService.Companion.startActionFoo

class TestMergeActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_merge2)
        startActionFoo(M3U8Library.getContext(), "/storage/emulated/0/11/aHR0cHM6Ly92b2QyLmtzc3pudXUuY24vMjAyMDA5MjUvTzVQUzZPOVYvaW5kZXgubTN1OA==", "m3u8")
    }
}