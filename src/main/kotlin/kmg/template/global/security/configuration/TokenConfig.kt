package kmg.template.global.security.configuration

import kmg.template.global.security.token.properties.TokenProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(TokenProperties::class)
class TokenConfig