package kmg.template.domain.user.persentation

import kmg.template.domain.user.data.RefreshRequest
import kmg.template.domain.user.data.TokenResponse
import kmg.template.domain.user.data.UserRequest
import kmg.template.domain.user.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: UserRequest)
        = userService.signUp(request)


    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: UserRequest): TokenResponse
        = userService.signIn(request)


    @PostMapping("/reissue")
    fun reissue(@RequestBody request: RefreshRequest): TokenResponse
        = userService.reissue(request)
}