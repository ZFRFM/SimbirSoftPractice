package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.faimizufarov.simbirtraining.databinding.ActivityAuthorizationBinding

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFromSavedInstanceState(savedInstanceState)

        setButtonEnabledState()

        binding.imageViewBack.setOnClickListener {
            this.finish()
        }

        binding.contentAuthorizationActivity.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle,
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        putInSavedInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun setButtonEnabledState() {
        val isEmailFilledObservable =
            binding
                .contentAuthorizationActivity
                .editTextEmail
                .toIsTextValidatedObservable()

        val isPasswordFilledObservable =
            binding
                .contentAuthorizationActivity
                .textInputLayoutPassword
                .editText
                ?.toIsTextValidatedObservable()

        val isButtonEnabledObservable =
            Observable.combineLatest(
                isEmailFilledObservable,
                isPasswordFilledObservable ?: Observable.just(false),
            ) { isEmailFilled, isPasswordFilled -> isEmailFilled && isPasswordFilled }

        isButtonEnabledObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { enabled ->
                binding.contentAuthorizationActivity.buttonSignIn.isEnabled = enabled
            }.let { disposables.add(it) }
    }

    private fun getFromSavedInstanceState(savedInstanceState: Bundle?) {
        binding
            .contentAuthorizationActivity
            .editTextEmail
            .setText(savedInstanceState?.getString(EMAIL_KEY))

        binding
            .contentAuthorizationActivity
            .editTextPassword
            .setText(savedInstanceState?.getString(PASSWORD_KEY))
    }

    private fun putInSavedInstanceState(outState: Bundle) {
        with(binding.contentAuthorizationActivity) {
            outState.putString(
                EMAIL_KEY,
                editTextEmail.text.toString(),
            )

            outState.putString(
                PASSWORD_KEY,
                editTextPassword.text.toString(),
            )
        }
    }

    companion object {
        const val EMAIL_KEY = "EMAIL_KEY"
        const val PASSWORD_KEY = "PASSWORD_KEY"
    }
}

fun EditText.toIsTextValidatedObservable() =
    textChanges()
        .map(CharSequence::toString)
        .map { text -> text.length >= 6 }
