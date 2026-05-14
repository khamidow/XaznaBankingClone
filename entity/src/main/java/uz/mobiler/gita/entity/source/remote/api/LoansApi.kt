package uz.mobiler.gita.entity.source.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import uz.mobiler.gita.entity.source.remote.request.LoanRepayRequest
import uz.mobiler.gita.entity.source.remote.request.LoanRequest
import uz.mobiler.gita.entity.source.remote.response.CardGeneralResponse
import uz.mobiler.gita.entity.source.remote.response.JustMessageResponse
import uz.mobiler.gita.entity.source.remote.response.LoanResponse

interface LoansApi {
    @GET("/api/v1/loans")
    @Headers("Requires-Auth: true")
    suspend fun getAllLoans(): Response<CardGeneralResponse<List<LoanResponse>>>

    @POST("/api/v1/loans")
    @Headers("Requires-Auth: true")
    suspend fun applyForLoan(@Body loanRequest: LoanRequest): Response<CardGeneralResponse<LoanResponse>>

    @POST("/api/v1/loans/{id}/repay")
    @Headers("Requires-Auth: true")
    suspend fun repayLoan(
        @Path("id") id: String,
        @Body loanRepayRequest: LoanRepayRequest
    ): Response<CardGeneralResponse<JustMessageResponse>>
}
