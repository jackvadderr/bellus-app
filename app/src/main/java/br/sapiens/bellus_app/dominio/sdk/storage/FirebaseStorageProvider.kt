package br.sapiens.bellus_app.dominio.sdk.storage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides methods to interact with Firebase Storage.
 *
 * @param storage The Firebase Storage instance.
 */
@Singleton
class FirebaseStorageProvider @Inject constructor() {

    private val storageReference = FirebaseStorage.getInstance().reference

    /**
     * Uploads a file to Firebase Storage.
     *
     * @param filePath The path where the file will be stored in Firebase Storage.
     * @return A LiveData that emits the download URL of the uploaded file.
     */
    fun uploadFile(filePath: String): MutableLiveData<String?> {
        Log.d("FirebaseStorageProvider", "uploadFile: Start uploading file: $filePath")
        val result = MutableLiveData<String?>()
        val fileUri = Uri.parse(filePath)
        storageReference.child(filePath).putFile(fileUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    Log.d("FirebaseStorageProvider", "uploadFile: File uploaded successfully: $uri")
                    result.postValue(uri.toString())
                }
            }.addOnFailureListener { exception ->
                Log.e("FirebaseStorageProvider", "uploadFile: Failed to upload file", exception)
                result.postValue(null)
            }
        return result
    }

    /**
     * Retrieves the download URL of a file from Firebase Storage.
     *
     * @param filePath The path of the file in Firebase Storage.
     * @return A LiveData that emits the download URL of the file.
     */
    fun getFileUrl(filePath: String): MutableLiveData<String?> {
        Log.d("FirebaseStorageProvider", "getFileUrl: Start getting file URL: $filePath")
        val result = MutableLiveData<String?>()
        storageReference.child(filePath).downloadUrl.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(
                    "FirebaseStorageProvider",
                    "getFileUrl: Failed to get file URL",
                    task.exception
                )
                result.postValue(null)
            } else {
                Log.d(
                    "FirebaseStorageProvider",
                    "getFileUrl: File URL retrieved successfully: ${task.result}"
                )
                result.postValue(task.result?.toString())
            }
        }
        return result
    }

    /**
     * Retrieves the download URLs of multiple files from Firebase Storage.
     *
     * @param filePaths The paths of the files in Firebase Storage.
     * @return A LiveData that emits a list of download URLs of the files.
     */
    fun getImageUrls(filePaths: List<String>): MutableLiveData<List<String>?> {
        Log.d("FirebaseStorageProvider", "getImageUrls: Start getting image URLs")
        val result = MutableLiveData<List<String>?>()
        val urls = mutableListOf<String>()
        filePaths.forEach { filePath ->
            storageReference.child(filePath).downloadUrl.addOnSuccessListener { uri ->
                Log.d("FirebaseStorageProvider", "getImageUrls: Image URL retrieved: $uri")
                urls.add(uri.toString())
                if (urls.size == filePaths.size) {
                    Log.d(
                        "FirebaseStorageProvider",
                        "getImageUrls: All image URLs retrieved successfully"
                    )
                    result.postValue(urls)
                }
            }.addOnFailureListener { exception ->
                Log.e(
                    "FirebaseStorageProvider",
                    "getImageUrls: Failed to get image URL for $filePath",
                    exception
                )
                result.postValue(null)
            }
        }
        return result
    }

    /**
     * Deletes a file from Firebase Storage.
     *
     * @param filePath The path of the file in Firebase Storage.
     * @return A LiveData that emits a boolean value indicating whether the file was successfully deleted.
     */
    fun deleteFile(filePath: String): LiveData<Boolean> {
        Log.d("FirebaseStorageProvider", "deleteFile: Start deleting file: $filePath")
        val result = MutableLiveData<Boolean>()
        storageReference.child(filePath).delete().addOnSuccessListener {
            Log.d("FirebaseStorageProvider", "deleteFile: File deleted successfully")
            result.postValue(true)
        }.addOnFailureListener { exception ->
            Log.e("FirebaseStorageProvider", "deleteFile: Failed to delete file", exception)
            result.postValue(false)
        }
        return result
    }
}