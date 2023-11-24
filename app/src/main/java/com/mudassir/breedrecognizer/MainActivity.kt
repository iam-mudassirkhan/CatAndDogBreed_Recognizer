package com.mudassir.breedrecognizer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mudassir.breedrecognizer.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    lateinit var imgView : ImageView
    lateinit var predictionBtn : Button
    lateinit var selectBtn : Button
    lateinit var cameraBtn : Button
    lateinit var result : TextView
    lateinit var bitmap: Bitmap
    lateinit var imageURI: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView = findViewById(R.id.img)
        predictionBtn = findViewById(R.id.predectImgBtn)
        selectBtn = findViewById(R.id.selectImgBtn)
        result = findViewById(R.id.result)
        cameraBtn = findViewById(R.id.cameraBtn)



        try {
            val intent = intent.getStringExtra("imageURI")
            imageURI = Uri.parse(intent)
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageURI)
            Glide.with(this).load(imageURI).into(imgView)
            //imgView.setImageBitmap(bitmap)

        }catch (e: Exception){
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }
        var  labels = application.assets.open("mylabels.txt").bufferedReader().readLines()

        var imageProcessor = ImageProcessor.Builder()
//            .add(NormalizeOp(0.0f, 255.0f))
//            .add(TransformToGrayscaleOp())
            .add(ResizeOp(224,224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        cameraBtn.setOnClickListener {

        val intent = Intent(this, Camera_Activity::class.java)
            startActivity(intent)

        }

        selectBtn.setOnClickListener {
            val intent = Intent()

            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 1000)
        }


        predictionBtn.setOnClickListener{
        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)

          tensorImage =  imageProcessor.process(tensorImage)

            val model = MobilenetV110224Quant.newInstance(this)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
            inputFeature0.loadBuffer(tensorImage.buffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0
            //result.text=outputFeature0.toString()
            outputFeature0.forEachIndexed { index, fl ->
                if (outputFeature0[maxIdx] < fl){
                    maxIdx = index
                   if(labels[index].isEmpty()){
                       result.text="No Match Found"
                   }else{
                       result.text=labels[maxIdx].toString()
                   }
                }



            }

          /*  when(labels[maxIdx]){
                "white wolf"->{
                    result.text = "white wolf"
                }
                "German shepherd"->{
                    result.text = "German Shepherd"
                }
                 "Siberian husky"->{
                    result.text = "Siberian husky"
                }

                "Labrador retriever"->{
                    result.text = "Labrador retriever"
                }

                "golden retriever"->{
                    result.text = "Golden Retriever"
                }

                "French bulldog"->{
                    result.text = "French bulldog"
                }

                "beagle"->{
                    result.text = "Beagle"
                }

                "malamute"->{
                    result.text = "Malamute"
                }

                "toy poodle"->{
                    result.text = "Poodle"
                }

                "Border collie"->{
                    result.text = "Border collie"
                }

                "Labrador retriever"->{
                    result.text = "Labrador retriever"
                }

                "Labrador retriever"->{
                    result.text = "Labrador retriever"
                }



                else ->{
                    result.text = "No Data Found"
                }
            }*/

          //result.setText(labels[maxIdx])

            // Releases model resources if no longer used.
            model.close()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK){
           // var uri = data?.data
            imageURI = data?.data!!
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageURI)
            Glide.with(this).load(imageURI).into(imgView)
           // imgView.setImageBitmap(bitmap)

        }
        else{
            Toast.makeText(this, "loading Image Cancel", Toast.LENGTH_SHORT).show()
        }
    }
}