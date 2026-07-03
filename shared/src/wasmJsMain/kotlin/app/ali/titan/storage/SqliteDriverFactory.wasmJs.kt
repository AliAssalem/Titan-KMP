package app.ali.titan.storage

import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker
import kotlin.js.ExperimentalWasmJsInterop

actual fun createSqliteDriver(): SQLiteDriver {
    return WebWorkerSQLiteDriver(createTitanWorker())
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun createTitanWorker(): Worker =
    js("""new Worker(new URL("sqlite-wasm-worker/worker.js", import.meta.url), { type: "module" })""")