package com.boniecki.moviedb.usecase

interface UseCase<R> {
    suspend fun execute(): R
}

interface SingleParamUseCase<P, R> {
    suspend fun execute(param: P): R
}