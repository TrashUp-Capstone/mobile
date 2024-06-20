package com.dicoding.trashup.ui.user.cart

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.MappingHelper
import com.dicoding.trashup.R
import com.dicoding.trashup.data.db.WasteAdapter
import com.dicoding.trashup.data.db.WasteHelper
import com.dicoding.trashup.data.entity.Waste
import com.dicoding.trashup.databinding.FragmentCartUserBinding
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.user.waiting.WaitingActivity
import com.dicoding.trashup.uriToFile
import com.dicoding.trashup.utils.reduceFileImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileNotFoundException

class CartUserFragment : Fragment() {

    private var _binding: FragmentCartUserBinding? = null
    private val viewModelCart: CartUserViewModel by viewModels()
    private val viewModelCreateActivity by activityViewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private var token: String? = null
    private var lat : Double? = null
    private var lon : Double? = null
    private var idAct  : String? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WasteAdapter
    private var sumPoints: Int = 0
    private var sumWeights : Double = 0.0


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartUserBinding.inflate(inflater, container, false)

        binding.btnAddWaste.setOnClickListener {
            startCamera()
        }

        viewModelCreateActivity. apply {
            userToken.observe(requireActivity()) {
                token = it
            }
            latitude.observe(requireActivity()) {
                lat = it
            }
            longitude.observe(requireActivity()) {
                lon = it
            }
            idActivity.observe(requireActivity()) {
                idAct = it
            }
            Log.d("CartUserFragment", "Token: ${token}, lat: ${lat}, lon: ${lon}, idAct: ${idAct}")
        }

        viewModelCart.apply {
            message.observe(requireActivity()) {
                showToast(it)
                Log.d("CartUserFragment", it)
            }

            messagePhoto.observe(requireActivity()) {
                showToast(it)
                Log.d("CartUserFragment", it)
            }
        }
        binding.btnConfirm.setOnClickListener {
           // deleteAllData()
            if (adapter.listWaste.isEmpty() || idAct.toString().isEmpty()) {
                showToast(requireContext().getString(R.string.error_empty))
            }
            else {
                submitWaste()
                confrimActivity()
            }
        }
        adapter = WasteAdapter(requireContext())
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.setHasFixedSize(true)
        loadWasteDataAsync()
        return binding.root
    }

    private fun deleteAllData() {
        lifecycleScope.launch {
            sumPoints = 0
            sumWeights = 0.0
            val wasteHelper = WasteHelper.getInstance(requireContext()) // Use requireContext() for fragment
            wasteHelper.open()

            try {
                val deletedRowCount = wasteHelper.deleteAllData()
                if (deletedRowCount.toString().isEmpty()) {
                    Log.i("CartUserFragment", "Successfully deleted $deletedRowCount waste items")
                    adapter.listWaste.clear() // Clear existing data in the adapter
                    showToast("Waste items deleted successfully")

                } else {
                    Log.d("CartUserFragment", "No waste items found to delete")
                    showToast("No waste items found")
                }
            } finally {
                wasteHelper.close() // Ensure database is closed even in case of exceptions
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun submitWaste() {
//        for (waste in adapter.listWaste) {
//            val weight = waste.weight
//            val wasteTypes = waste.typeWaste
//            val wasteId = waste.id
//
//            // Buat mengirim photo ke API
//            val photoUri = waste.photo.toUri()
//            Log.d("SubmitWaste", "Photo URI: $photoUri")
//
//            if (photoUri.scheme == "file") {
//                val imageFile = File(photoUri.path!!)
//                val reducedImageFile = imageFile.reduceFileImage()
//                val reqImageFile = reducedImageFile.asRequestBody("image/jpeg".toMediaType())
//                val multipartBody = MultipartBody.Part.createFormData(
//                    "photo",
//                    reducedImageFile.name,
//                    reqImageFile
//                )
//                viewModelCart.SubmitWaste(token.toString(), idAct.toString(), wasteId.toString(), weight.toString(), wasteTypes)
//                viewModelCart.submitPhotoWaste(token.toString(), multipartBody)
//            } else if (photoUri.scheme == "content") {
//                try {
//                    val imageFile = uriToFile(photoUri, requireContext()).reduceFileImage()
//                    val reqImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//                    val multipartBody = MultipartBody.Part.createFormData(
//                        "photo",
//                        imageFile.name,
//                        reqImageFile
//                    )
//                    viewModelCart.SubmitWaste(token.toString(), idAct.toString(), wasteId.toString(), weight.toString(), wasteTypes)
//                    viewModelCart.submitPhotoWaste(token.toString(), multipartBody)
//                } catch (e: FileNotFoundException) {
//                    Log.e("SubmitWaste", "File not found: ${photoUri.path}", e)
//                    Toast.makeText(requireContext(), "Photo file not found", Toast.LENGTH_SHORT).show()
//                } catch (e: Exception) {
//                    Log.e("SubmitWaste", "Error processing file: ${photoUri.path}", e)
//                    Toast.makeText(requireContext(), "Error processing photo", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Log.e("SubmitWaste", "Unknown URI scheme: ${photoUri.scheme}")
//                Toast.makeText(requireContext(), "Invalid photo URI", Toast.LENGTH_SHORT).show()
//            }
//
//            Log.d("CartUserFragment", "weight = ${weight}, wasteTypes = ${wasteTypes}, wasteId = ${wasteId}")
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun submitWaste() {
        for (waste in adapter.listWaste) {
            val weight = waste.weight
            val wasteTypes = waste.typeWaste
            val wasteId = waste.id
            viewModelCart.SubmitWaste(token.toString(), idAct.toString(), wasteId.toString(), weight.toString(), wasteTypes)
            Log.d("CartUserFragment", "weight = ${weight}, wasteTypes = ${wasteTypes}, wasteId = ${wasteId}")

            // Buat mengirim photo ke API
//            val photo = waste.photo.toUri()
//            val imageFile = uriToFile(photo, requireContext()).reduceFileImage()
//            val reqImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                reqImageFile
//            )
//          viewModelCart.submitPhotoWaste(token.toString(), multipartBody)
        }
        sendWaste()
    }
    private fun loadWasteDataAsync() {
        lifecycleScope.launch {
            val wasteHelper = WasteHelper.getInstance(requireContext()) // Use requireContext() for fragment
            wasteHelper.open()
            val deferredWastes = this.async(Dispatchers.IO) {
                val cursor = wasteHelper.queryAll()
                val wastes = MappingHelper.mapCursorToArrayList(cursor) // Assuming Waste class exists
                wastes
            }

            val wastes = deferredWastes.await()
            if (wastes.size > 0) {
                Log.e("CartUserFragment", wastes.size.toString())
                adapter.listWaste = wastes
                binding.rvCart.adapter = adapter
            } else {
                binding.rvCart.adapter = null
                showToast("No waste items found")
            }
            wasteHelper.close()
        }
        sumPoints = adapter.getPoints()
        sumWeights = adapter.getWeights()
        Log.d("CartUserFragment", "Di sini Total Points : ${sumPoints}, Total Weight = ${sumWeights}")
    }

    private fun sendWaste() {
        viewModelCart.sendWaste(token.toString(), idAct.toString(), sumWeights.toString(), sumPoints.toString())
    }


    private fun showToast(message: String) {
        if (isAdded) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun confrimActivity() {
        val intent = Intent(requireContext(), WaitingActivity::class.java)
        startActivity(intent)
    }

    private val startCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Perform any async operation here before navigating to the cart fragment
                navigateToCart()
            } else {
                Toast.makeText(requireContext(), "Camera activity was canceled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun navigateToCart() {
        findNavController().navigate(R.id.navigation_cart_user)
    }

    private fun startCamera() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        startCameraLauncher.launch(intent)
    }

    override fun onResume() {
        super.onResume()
        loadWasteDataAsync()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}