package com.example.postapi

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.postapi.model.PostsItem
import com.example.postapi.networking.fetchAllData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }

    val focusedColor = Color(0xFF6200EE)  // Accent color when focused
    val unfocusedColor = Color(0xFFE0E0E0)

    var postsList: List<PostsItem> by remember { mutableStateOf(emptyList()) }
    postsList = fetchAllData()

    val filteredPostList = if (searchQuery.isEmpty()) {
        postsList
    } else {
        postsList.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
    Log.d("TAG after seach", "MainScreen: $filteredPostList")

    Scaffold(
        topBar = {

            TopAppBar(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .height(130.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                        text = "Posts",
                        fontSize = 30.sp,
                    ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    if (isSearchVisible){
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search Post",
                                    style = TextStyle(color = Color.Gray),
                                    modifier = Modifier,
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    tint = Color.Gray,
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close Icon",
                                    tint = Color.Gray,
                                    modifier = Modifier.clickable {
                                        if (searchQuery.isEmpty()){
                                            isSearchVisible = false
                                        }
                                        if (searchQuery.isNotEmpty()) {
//                                            searchQuery = ""
                                        }
                                    }
                                )
                            },
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
//                        .padding(top = 20.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    if (searchQuery.isNotEmpty()) focusedColor else unfocusedColor,
                                    RoundedCornerShape(12.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,  // Remove the default underline
                                unfocusedIndicatorColor = Color.Transparent,  // Remove the default underline
                                containerColor = Color.White,
                                cursorColor = focusedColor
                            ),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                            singleLine = true
                        )
                    } else {
                        IconButton(
                            onClick = { isSearchVisible = true },
                            modifier = Modifier
                                .padding(top = 20.dp, end = 20.dp)
                                .size(35.dp)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredPostList) {
                        Log.d("TAGxo'ja", "MainScreen: ${it.title}")
                        PostItem(post = it, navHostController = navHostController)
                    }
                }

            }
        }
    }


}

@Composable
fun PostItem(post: PostsItem, navHostController: NavHostController){

    Card(modifier = Modifier
        .clickable {
            navHostController.navigate("onePost/${post.id}")
        }) {
        Column(modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 15.dp)
            .fillMaxWidth(),) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "User ID: ${post.userId}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Post ID: ${post.id}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(text = "${post.title}",
                color = Color.Black,
                fontSize = 18.sp,
            )
        }



    }
}