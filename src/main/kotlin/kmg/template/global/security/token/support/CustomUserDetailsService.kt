package kmg.template.global.security.token.support

import kmg.template.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
): UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(email: String): UserDetails {
        return CustomUserDetails (
            user = userRepository.findByEmail(email)?: throw RuntimeException("user not found")
        )
    }
}