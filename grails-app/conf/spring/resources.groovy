import com.gestion.tache.security.CorsFilter
import com.gestion.tache.security.UserPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
    corsFilter(CorsFilter)
}
