package uz.mobiler.gita.entity.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.mobiler.gita.core.models.TransactionData
import uz.mobiler.gita.entity.source.remote.api.UsersApi
import java.io.IOException
import javax.inject.Inject

class TransactionPagingSource @Inject constructor(
    private val api: UsersApi,
    private val cardId: String,
    private val type: String
) : PagingSource<Int, TransactionData>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, TransactionData> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getTransactionHistory(
                page = currentPage,
                pageSize = params.loadSize,
                cardId = cardId,
                type = type
            )
            if (response.isSuccessful) {
                val data = response.body()?.data?.map {
                    TransactionData(
                        it.id,
                        it.type,
                        it.amount,
                        it.currency,
                        it.description,
                        it.status,
                        it.cardId,
                        it.createdAt
                    )
                } ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)

        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, TransactionData>
    ): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)

            anchorPage?.prevKey?.plus(1)
                ?: anchorPage?.nextKey?.minus(1)
        }
    }
}