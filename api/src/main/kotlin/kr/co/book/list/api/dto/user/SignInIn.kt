package kr.co.book.list.api.dto.user

import javax.validation.constraints.NotEmpty

data class SignInIn(

    @field:NotEmpty
    val userId : String,
    @field:NotEmpty
    val password : String,

)
