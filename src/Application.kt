package agiani

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.http.content.*
import io.ktor.auth.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.sessions.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    var messages = listOf<GuestMessage>()

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    routing {

        post("/") {
            val formData = call.receiveParameters()
            val name = formData.get("name") ?: "<NO NAME>"
            val message = formData.get("message") ?: "<NO MESSAGE>"
            messages = messages + GuestMessage(name, message)
            call.application.environment.log.info("Name: $name, message: $message")
            call.respondRedirect("/")
        }

        get("/") {
            call.application.environment.log.info("Someone entered to guest book")
            call.respond(FreeMarkerContent("index.ftl", mapOf("messages" to messages), ""))
        }

        static("/static") {
            resources("static")
        }

    }
}

data class GuestMessage(val name: String, val message: String)

data class MySession(val count: Int = 0)

