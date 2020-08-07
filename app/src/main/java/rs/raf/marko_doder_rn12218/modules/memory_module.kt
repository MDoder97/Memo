package rs.raf.marko_doder_rn12218.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.marko_doder_rn12218.data.datasource.local.Database
import rs.raf.marko_doder_rn12218.data.repositories.MemoryRepository
import rs.raf.marko_doder_rn12218.data.repositories.MemoryRepositoryImpl
import rs.raf.marko_doder_rn12218.presentation.viewmodel.MemoryViewModel

val memoryModule = module {

    viewModel { MemoryViewModel(memoryRepository = get()) }

    single<MemoryRepository> { MemoryRepositoryImpl(localDataSource = get())}

    single { get<Database>().getMemoryDao() }


}