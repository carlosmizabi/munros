import java.net.URL

val EmptyUrl = URL("")

val URL.isEmpty get () = this == EmptyUrl