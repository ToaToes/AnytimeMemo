package com.example.anytimememo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun register(email: String, password: String, onResult:  (String) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    onResult("Registration successful")
                } else {
                    onResult("Registration failed: ${task.exception?.message}")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.SignOut
    }

    //not signed in but click on sign out
    fun unauthenticate(){
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object SignOut : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}