package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.Green
import com.example.littlelemon.ui.theme.LittleLemonColor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LowerPanel(context: Context, lifecycleScope: LifecycleCoroutineScope) {


    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "database2").build()
    }

    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response = httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
        return response.menuItems ?: listOf()
    }

    fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }

    lifecycleScope.launch(Dispatchers.IO) {
        if (database.menuItemDao().isEmpty()) {
            saveMenuToDatabase(fetchMenu())
        }
    }

    // add menuItems variable here
    var menuItems = databaseMenuItems

    val searchPhrase = remember { mutableStateOf("") }
    if (searchPhrase.value.isNotEmpty()) { menuItems = menuItems.filter {it.title.contains(searchPhrase.value, ignoreCase = true)}}

    val categoryFilter = remember { mutableStateOf("") }
    if (categoryFilter.value.isNotEmpty()) { menuItems = menuItems.filter {it.category.equals(categoryFilter.value)}}

    Column {
        Column(
            Modifier
                .background(Green)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = searchPhrase.value,
                onValueChange = {searchPhrase.value = it},

                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors( backgroundColor = LittleLemonColor.cloud),
                placeholder = { Text(text = "Enter search phrase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search icon") }
            )
        }
        Column(Modifier.padding(20.dp)) {
            Text(text = "ORDER FOR DELIVERY!")
            Row {
                Button(onClick = { categoryFilter.value = "" },
                    Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.cloud),
                    shape = RoundedCornerShape(50),
                ) { Text(text = "All")}

                Button(onClick = { categoryFilter.value = "starters" },
                    Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.cloud),
                    shape = RoundedCornerShape(50),
                ) { Text(text = "Starters ")}

                Button(onClick = { categoryFilter.value = "mains" },
                    Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.cloud),
                    shape = RoundedCornerShape(50),
                ) { Text(text = "Mains ")}

                Button(onClick = { categoryFilter.value = "desserts" },
                    Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = LittleLemonColor.cloud),
                    shape = RoundedCornerShape(50),
                ) { Text(text = "Desserts ")}
            }
            Divider(
                modifier = Modifier.padding(top = 12.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuDish(menuItems)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuDish(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row() {
                    Column {
                        Text(text = menuItem.title, style = MaterialTheme.typography.h2,
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .padding(bottom = 5.dp)

                        )
                        Text(text = menuItem.description, style = MaterialTheme.typography.body1,
                            maxLines = 2,
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .padding(bottom = 10.dp)
                        )
                        Text(text = "$${menuItem.price}", style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .fillMaxWidth(0.75f)

                        )
                    }
                    GlideImage(model = menuItem.image,
                        contentDescription = menuItem.title,
                        modifier = Modifier.size(100.dp, 100.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    thickness = 1.dp,
                    color = LittleLemonColor.cloud
                )
            }
        )
    }

}