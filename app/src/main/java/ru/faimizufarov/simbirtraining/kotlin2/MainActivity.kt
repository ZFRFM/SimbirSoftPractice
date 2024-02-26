package ru.faimizufarov.simbirtraining.kotlin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.faimizufarov.simbirtraining.R

const val TAG = "User TAG"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User(1, "Alex", 28, Type.DEMO)
        Log.d(TAG, user.startTime.toString())

        Thread.sleep(1000)
        Log.d(TAG, user.startTime.toString())

        val userList = mutableListOf<User>(user).apply {
            this.add(User(id = 2, name = "Bob", age = 32, type = Type.DEMO))
            this.add(User(id = 3, name = "Dwayne", age = 51, type = Type.FULL))
            this.add(User(id = 4, name = "David", age = 43, type = Type.FULL))
            this.add(User(id = 5, name = "Jackie", age = 67, type = Type.FULL))
        }

        val userWithFullAccess = userList.filter { it.type == Type.FULL }

        val userNames = userList.map { it.name }
        Log.d(TAG, userNames.first())
        Log.d(TAG, userNames.last())

        val anonObject = object : AuthCallback {
            override fun authSuccess() {
                Log.d(TAG, "You are welcome!")
            }

            override fun authFailed() {
                Log.d(TAG, "You are not signed!")
            }
        }

        auth(user, anonObject, ::updateCache)

        val action = Login(user)
        doAction(action, user, anonObject, ::updateCache)
    }

    inline fun auth(user: User, authCallback: AuthCallback, updateCache: () -> Unit) {
        if (user.isAdult()) {
            authCallback.authSuccess()
            updateCache.invoke()
        } else {
            authCallback.authFailed()
        }
    }

    fun updateCache() {
        Log.d(TAG, "Как может выглядеть информация об обновлении кэша?")
    }

    fun doAction(action: Action, user: User, authCallback: AuthCallback, updateCache: () -> Unit) {
        when(action) {
            is Registration -> Log.d(TAG, "Registration started")
            is Login -> auth(user, authCallback, updateCache)
            is Logout -> Log.d(TAG, "Logout started")
        }
    }
}

enum class Type {
    DEMO, FULL
}