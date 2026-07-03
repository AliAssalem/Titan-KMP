package app.ali.titan.storage

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun createSqliteDriver(): SQLiteDriver = BundledSQLiteDriver()