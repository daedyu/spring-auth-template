package kmg.template.domain.user.service

import kmg.template.domain.user.data.RefreshRequest
import kmg.template.domain.user.data.TokenResponse
import kmg.template.domain.user.data.UserRequest
import kmg.template.domain.user.entity.UserEntity
import kmg.template.domain.user.repository.UserRepository
import kmg.template.global.security.token.core.TokenParser
import kmg.template.global.security.token.core.TokenProvider
import kmg.template.global.security.token.core.TokenValidator
import kmg.template.global.security.token.enumeration.TokenType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class UserService(
    private val userRepository: UserRepository,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val tokenValidator: TokenValidator,
    private val tokenParser: TokenParser
) {
    fun signUp(request: UserRequest) {
        userRepository.save(request.toEntity())
    }

    fun signIn(request: UserRequest): TokenResponse {
        val user: UserEntity = userRepository.findByEmail(request.email)?: throw RuntimeException("User not found")
        if (!bcryptPasswordEncoder.matches(request.password, request.password)) throw RuntimeException("Invalid password")
        return TokenResponse(
            access = tokenProvider.generateAccess(user),
            refresh = tokenProvider.generateRefresh(user)
        )
    }

    fun reissue(request: RefreshRequest): TokenResponse {
        tokenValidator.validateAll(request.refresh, TokenType.REFRESH_TOKEN)
        val user: UserEntity = userRepository.findByEmail(tokenParser.findEmail(request.refresh))?: throw RuntimeException("User not found")
        return TokenResponse(
            access = tokenProvider.generateAccess(user),
            refresh = tokenProvider.generateRefresh(user)
        )
    }
}