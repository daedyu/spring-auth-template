package kmg.template.domain.user.entity

import jakarta.persistence.*
import kmg.template.domain.user.enumeration.UserRole
import java.util.*

@Entity(name = "user_tbl")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(columnDefinition = "text")
    var password: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.USER,

    // add your user data here
)