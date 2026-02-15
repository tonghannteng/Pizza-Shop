package com.tonghannteng.turo.di

import com.tonghannteng.turo.presentation.main.MainViewModel
import com.tonghannteng.turo.data.HttpClientFactory
import com.tonghannteng.turo.data.MainRepository
import com.tonghannteng.turo.data.MainRepositoryImpl
import com.tonghannteng.turo.presentation.detail.DetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinMainModule = module {

    single { HttpClientFactory.create() }
    singleOf(::MainRepositoryImpl).bind<MainRepository>()
    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
}
