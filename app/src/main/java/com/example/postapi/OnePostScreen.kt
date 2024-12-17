package com.example.postapi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.postapi.model.PostsItem
import com.example.postapi.networking.Retrofit
import retrofit2.Callback
import retrofit2.Response

@Composable
fun OnePostScreen(navHostController: NavHostController, id: Int) {
//    val recipe = navHostController.previousBackStackEntry?.savedStateHandle?.get<Recipe>("recipe")

    val defaultStartPadding = 20.dp
    val defaultTopPadding = 10.dp

    var isLoading by remember { mutableStateOf(true) }
    var post0: PostsItem? by remember { mutableStateOf(null) }
    val call = Retrofit.api.getPostById(id)
    call.enqueue(object : Callback<PostsItem> {
        override fun onResponse(
            call: retrofit2.Call<PostsItem>,
            response: Response<PostsItem>
        ) {
            if (response.isSuccessful) {
                val postsResponse = response.body()
                postsResponse?.let {
                    post0 = it
                    Log.d("TAGbek", "onResponse: ${it.title}")
                }
                isLoading = false
            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        }

        override fun onFailure(call: retrofit2.Call<PostsItem>, t: Throwable) {
            Log.d("TAGbek", "onFailure: failed")
        }
    })



    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Center the progress indicator
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp) // Adjust the size of the progress indicator
            )
        }
    } else {
        val post = requireNotNull(post0) { "Recipe cannot be null" }
//        var recipe = Recipe(10, 10, "aads", "hard", 1, "", listOf("potato", "tomato"), listOf("cook", "boil", "freeze"), listOf("dinner"), "Pizza", 10, 5.0, 100, 1, listOf("Italy"), 100)
//        recipe?.let { recipe ->
//            // Display the recipe details
//            Text(text = recipe.name)
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = defaultTopPadding+40.dp)
                .padding(horizontal = defaultStartPadding)
        ) {
            IconButton(
                onClick = {
                    navHostController.navigate("main")
                },
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(50.dp)
                    .alpha(0.9f)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize(),
                    tint = Color.Black
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "User ID: ${post.userId}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Post ID: ${post.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = post.title,
                modifier = Modifier
                    .padding(top = 20.dp),
                color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = post.body,
                modifier = Modifier.padding(top = 10.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )


        }

        
    }
//    Log.d("TAGlola", "OneRecipeScreen: ${recipe0}")
//    Text(text = recipe!!.name)
}