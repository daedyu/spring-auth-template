package kmg.template.global.security.token.core

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import kmg.template.global.security.token.enumeration.TokenType
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenValidator(
    private val parser: TokenParser,
) {
    fun validateAll(token: String, tokenType: TokenType) {
        validate(token)
        validateType(token, tokenType)
    }

    fun validateType(token: String, tokenType: TokenType) {
        if (parser.findType(token) != tokenType) throw RuntimeException("token mismatch")
    }

    fun validate(token: String) {
        try {
            parser.findType(token)
            if (
                parser.findExpiration(token).before(Date())
            ) throw RuntimeException("token expired")
        } catch (e: ExpiredJwtException) {
            throw RuntimeException("token expired")
        } catch (e: SignatureException) {
            throw RuntimeException("token signature invalid")
        } catch (e: Exception) {
            throw RuntimeException("token unknown")
        }
    }
}