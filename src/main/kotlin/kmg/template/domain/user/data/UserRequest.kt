package kmg.template.domain.user.data

import kmg.template.domain.user.entity.UserEntity

data class UserRequest(
    val email: String,
    val password: String,
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            password = password
        )
    }
}