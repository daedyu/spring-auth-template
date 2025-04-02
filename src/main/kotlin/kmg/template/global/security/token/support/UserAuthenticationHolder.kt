package kmg.template.global.security.token.support

import kmg.template.domain.user.entity.UserEntity
import org.springframework.security.core.context.SecurityContextHolder

object UserAuthenticationHolder {
    fun current(): UserEntity {
        return (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).user
    }
}