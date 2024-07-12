package com.aevans

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.html.*
import kotlinx.serialization.Serializable

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(CallLogging)
    install(ContentNegotiation) {
        json()
    }
    routing {
//        static(remotePath = "assets") {
//            //For static routing "/assets/filename.txt
//            resources("static")
//        }
        get("/")
        {
            call.respondText("Hello, World!")
        }
        get("/welcome")
        {
            val name = call.request.queryParameters["name"]
            call.respondHtml {
                head {
                    title { + "Custom Title" }
                }
                body {
                    if (name.isNullOrEmpty()) {
                        h3 {
                            + "Welcome"
                        }
                    } else {
                        h3 {
                            + "Welcome $name !"
                        }
                    }
                    p {
                        + "Current directory is: ${System.getProperty("user.dir")}"
                    }
//                    img { src = "logo.jpg" }
                }
            }
        }
        get("/users/{username}") {
            val username = call.parameters["username"]
            val header = call.request.headers["Connection"]
            if (username == "Admin") {
                call.response.header(name = "CustomHeader", "Admin")
                call.respond(message = "Hello Admin", status = HttpStatusCode.OK)
            }
            call.respondText("Greetings, $username with header: $header")
        }
        get("/user") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText("Hi, my name is $name, I'm $age years old!")
        }
        get("/person") {
            try {
                val person = Person(name = "John", age = 25)
                call.respond(message = person, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get("/redirect") {
            call.respondRedirect(url = "/moved", permanent = false)
        }
        get("/moved") {
            call.respondText("You have been redirected")
        }
    }
}

@Serializable
data class  Person(
    val name: String,
    val age: Int
)