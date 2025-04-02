package kmg.template.global.security.token.core

import io.jsonwebtoken.Jwts
import kmg.template.domain.user.entity.UserEntity
import kmg.template.global.security.token.enumeration.TokenType
import kmg.template.global.security.token.properties.TokenProperties
import org.springframework.stereotype.Component
import java.lang.System.currentTimeMillis
import java.util.*

@Component
class TokenProvider(
    private val properties: TokenProperties,
) {
    private fun generate(tokenType: TokenType, user: UserEntity, expire: Long): String {
        return Jwts.builder()
            .claim("category", tokenType.value)
            .claim("email", user.email) // change claim you want
            .claim("role", user.role)
            .issuedAt(Date(currentTimeMillis()))
            .expiration(Date(currentTimeMillis() + expire))
            .signWith(properties.secretKey())
            .compact()
    }

    fun generateAccess(user: UserEntity): String
            = generate(TokenType.ACCESS_TOKEN, user, properties.access)

    fun generateRefresh(user: UserEntity): String {
        val refreshToken = generate(TokenType.REFRESH_TOKEN, user, properties.refresh)
//        tokenRedisService.storeRefreshToken(user.username, refreshToken) -> if using redis, use this code
        return refreshToken
    }
}