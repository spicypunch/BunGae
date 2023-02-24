package com.example.bungae.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.bungae.database.ItemData
import com.example.bungae.ui.update.UpdatePostActivity


class ActivityContract : ActivityResultContract<ItemData, HashMap<String, String>?>() {

    override fun createIntent(context: Context, input: ItemData): Intent {
        return Intent(context, UpdatePostActivity::class.java).apply {
            putExtra("item", input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): HashMap<String, String>? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.getSerializableExtra("result") as HashMap<String, String>
            else -> null
        }
    }
}