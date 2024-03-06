package ru.faimizufarov.simbirtraining.kotlin.kotlin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.faimizufarov.simbirtraining.R

const val TAG = "User TAG"

class KotlinActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val user = User(1, "Alex", 28, Type.DEMO)
        Log.d(TAG, user.startTime.toString())

        Thread.sleep(1000)
        Log.d(TAG, user.startTime.toString())

        val userList = mutableListOf<User>(user).apply {
            add(User(id = 2, name = "Bob", age = 32, type = Type.DEMO))
            add(User(id = 3, name = "Dwayne", age = 51, type = Type.FULL))
            add(User(id = 4, name = "David", age = 43, type = Type.FULL))
            add(User(id = 5, name = "Jackie", age = 67, type = Type.FULL))
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

        val action = Action.Login(user)
        doAction(action, user, anonObject, ::updateCache)
    }

    inline fun auth(user: User,
                    authCallback: AuthCallback,
                    updateCache: (Boolean, User, String) -> Unit) {
        if (user.isAdult()) {
            authCallback.authSuccess()
            updateCache(user.isAdult(), user)
        } else {
            authCallback.authFailed()
            updateCache(
                user.isAdult(),
                user,
                "Пользователь не достиг 18 лет! В эту систему ему вход запрещён!"
            )
        }
    }

    fun updateCache(status: Boolean, user: User, cause: String = "") {
        if (status) Log.d(TAG, "Пользователь ${user.name} был залогинен")
        else Log.d(TAG, "Пользователь ${user.name} завалил авторизацию по причине: $cause")
    }

    fun doAction(action: Action, user: User,
                 authCallback: AuthCallback,
                 updateCache: (Boolean, User, String) -> Unit) {
        when(action) {
            is Action.Registration -> Log.d(TAG, "Registration started")
            is Action.Login -> auth(user, authCallback, updateCache)
            is Action.Logout -> Log.d(TAG, "Logout started")
        }
    }
}