package rs.raf.marko_doder_rn12218.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_saved_memories_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.contract.MemoryContract
import rs.raf.marko_doder_rn12218.presentation.view.activities.EditMemoryActivity
import rs.raf.marko_doder_rn12218.presentation.view.recycler.adapter.MemoryAdapter
import rs.raf.marko_doder_rn12218.presentation.view.state.InsertMemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.MemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.ToggleMemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.UpdateMemoryState
import rs.raf.marko_doder_rn12218.presentation.viewmodel.MemoryViewModel
import timber.log.Timber

class SavedMemoriesListFragment : Fragment(R.layout.fragment_saved_memories_list) {

    companion object {
        const val EDIT_KEY_REQUEST_CODE = 123
        const val MEMORY_EDIT = "edit"
    }

    private val memoryViewModel : MemoryContract.ViewModel by
    sharedViewModel<MemoryViewModel>()

    private lateinit var adapter: MemoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUI()
        initListeners()
        initObservers()
    }

    private fun initUI() {
        initRecycler()
    }

    private fun initListeners() {
        searchEt.doAfterTextChanged {
            memoryViewModel.getByTitleAndContent(it.toString())
        }
        sortFb.setOnClickListener {
            memoryViewModel.toggleMemories()
            if(memoryViewModel.isNewest())
                sortFb.setImageResource(R.drawable.ic_oldest_24dp)
            else
                sortFb.setImageResource(R.drawable.ic_newest_24dp)
        }
    }

    private fun initRecycler() {
        memoriesRv.layoutManager = LinearLayoutManager(context)
        adapter = MemoryAdapter {
            val intent = Intent(context, EditMemoryActivity::class.java)
            intent.putExtra(EditMemoryActivity.EDIT_KEY, it)

            startActivityForResult(intent, EDIT_KEY_REQUEST_CODE)
        }
        memoriesRv.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            EDIT_KEY_REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK) {
                    val memory: Memory? = data?.getParcelableExtra(MEMORY_EDIT)
                    if (memory != null) {
                        memoryViewModel.updateMemory(memory)
                    }
                }
            }
        }
    }

    private fun initObservers() {
        memoryViewModel.memoryState
            .observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
        memoryViewModel.insertMemoryState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is InsertMemoryState.Success -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is InsertMemoryState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        memoryViewModel.updateMemoryState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is UpdateMemoryState.Success -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is UpdateMemoryState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        memoryViewModel.getAllMemories()
    }

    private fun renderState(state: MemoryState) {
        when(state) {
            is MemoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.memories)
            }
            is MemoryState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MemoryState.Loading -> {
                showLoadingState(false)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        memoriesRv.isVisible = !loading
    }
}