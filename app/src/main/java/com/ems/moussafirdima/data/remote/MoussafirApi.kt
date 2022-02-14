package com.ems.moussafirdima.data.remote

import com.ems.moussafirdima.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface MoussafirApi {

    @GET("trips/stations/")
    suspend fun getAllStations(): List<StationDto>

    @POST("trips/tickets/{trip_id}")
    suspend fun getAllTicketsForTrip(@Path("trip_id") id: Int): List<TicketDto>

    @POST("trips/tickets/client/{token}")
    suspend fun getAllTicketsForClient(@Path("token") token: String): List<TicketDto>

    @GET("trips/trips/{start}/{destination}")
    suspend fun getTrips(@Path("start") start: String,
                         @Path("destination") destination: String): List<TripDto>

    @GET("trips/trips/")
    suspend fun getAllTrips(): List<TripDto>

    @POST("users/client/{token}")
    suspend fun getClient(@Path("token") token: String): ClientDto

    @POST("users/client/")
    suspend fun addClient(@Body client: UserDto): ClientDto

    @FormUrlEncoded
    @POST("users/login/")
    suspend fun loginClient(
        @Field("username") phoneNumber: String,
        @Field("password") password: String
    ): Map<String, String>

    @FormUrlEncoded
    @POST("users/autologin/")
    suspend fun autoLogin(@Field("token") token: String): Response<Void>

    @FormUrlEncoded
    @POST("trips/ticket/")
    suspend fun addTicket(
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("num_of_tickets") numOfTickets: Int,
        @Field("token") token: String,
        @Field("trip_pk") tripId: Int
    ): Response<Void>

    @FormUrlEncoded
    @POST("trips/ticket/update/{id}")
    suspend fun updateTicket(
        @Path("id") id: Int,
        @Field("status") string: String
    ): Response<Void>

    @POST("codes/code/{number}")
    suspend fun verifyPhoneNumber(@Path("number") phoneNumber: String): CodeDto


}