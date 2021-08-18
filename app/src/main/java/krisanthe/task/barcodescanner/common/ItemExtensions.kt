package krisanthe.task.barcodescanner.common

import krisanthe.task.barcodescanner.database.DataEntity
import krisanthe.task.barcodescanner.model.Item

fun List<DataEntity>.toItems(): List<Item> =
    map { entity ->
        Item(
            entity.data,
            entity.timestamp,
            entity.name
        )
    }

fun Item.toEntity() =
    DataEntity(
        data = code,
        timestamp = timestamp,
        name = name
    )
