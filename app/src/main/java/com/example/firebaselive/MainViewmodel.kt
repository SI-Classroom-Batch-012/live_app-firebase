package com.example.firebaselive

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaselive.model.Note
import com.example.firebaselive.model.Profile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class MainViewmodel : ViewModel() {


    val auth = Firebase.auth
    val firestore = Firebase.firestore
    val storage = Firebase.storage

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user

    //Das profile Document enthält ein einzelnes Profil(das des eingeloggten Users)
    //Document ist wie ein Objekt
    lateinit var profileRef: DocumentReference

    //Die note Collection enthält beliebig viele Notes(alle notes des eingeloggten Users
    //Collection ist wie eine Liste
    lateinit var notesRef: CollectionReference

    init {
        setupUserEnv()
    }

    //region FirebaseUserManagement


    //Richtet die Variablen ein die erst eingerichtet werden können
    //wenn der User eingeloggt ist
    fun setupUserEnv() {

        _user.value = auth.currentUser

        //Alternative Schreibweise um auf null Werte zu überprüfen
        auth.currentUser?.let { firebaseUser ->

            profileRef = firestore.collection("user").document(firebaseUser.uid)
            notesRef = firestore.collection("user").document(firebaseUser.uid).collection("notes")
//            notesRef = profileRef.collection("notes")

        }

    }

    fun register(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //User wurde erstellt
                setupUserEnv()

                val newProfile = Profile()
                profileRef.set(newProfile)

            } else {
                //Fehler aufgetreten
            }
        }

    }

    fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //User wurde eingeloggt
                setupUserEnv()
            } else {
                //Fehler aufgetreten
            }
        }
    }

    fun logout() {

        auth.signOut()
        setupUserEnv()

    }


    fun uploadProfilePicture(uri: Uri) {

        val imageRef = storage.reference.child("images/${auth.currentUser!!.uid}/profilePicture")
        imageRef.putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                imageRef.downloadUrl.addOnCompleteListener { finalImageUrl ->
                    profileRef.update("profilePicture", finalImageUrl.result.toString())
                }
            }
        }
    }

    fun saveNote(title: String, content: String) {

        val newNote = Note(
            title = title,
            content = content
        )

        notesRef.add(newNote)
    }

    //endregion


}