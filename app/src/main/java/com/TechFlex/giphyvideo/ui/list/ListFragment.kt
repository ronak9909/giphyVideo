package com.TechFlex.giphyvideo.ui.list

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.TechFlex.giphyvideo.BuildConfig
import com.TechFlex.giphyvideo.R
import com.TechFlex.giphyvideo.di.component.DaggerFragmentComponent
import com.TechFlex.giphyvideo.di.module.FragmentModule
import com.TechFlex.giphyvideo.ui.list.domain.model.Giphy
import com.TechFlex.giphyvideo.ui.base.BaseController
import com.TechFlex.giphyvideo.ui.detail.DetailFragment
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import javax.inject.Inject

class ListFragment : BaseController(), ListContract.View, TextWatcher {

    @BindView(R.id.rec_video_thumb)
    lateinit var mVideoThubnailRec: RecyclerView

    @BindView(R.id.edt_search)
    lateinit var mSearchEdt: EditText

    @BindView(R.id.progressBar)
    lateinit var mProgressBar: ProgressBar

    @Inject
    lateinit var presenter: ListContract.Presenter

    var limit: Int = 50;
    var offset: Int = 0;

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override protected fun onViewBound(view: View) {
        super.onViewBound(view)
        injectDependency()
        presenter.attach(this)
        mSearchEdt.addTextChangedListener(this)
    }


    private fun injectDependency() {
        val activityComponent = DaggerFragmentComponent.builder().fragmentModule(FragmentModule()).build()
        activityComponent.inject(this)
    }


    private fun setGiphyListAdapter(giphySearchList: List<Giphy.GiphyData>) {
        val giphyListAdapter = GiphyListAdapter(router, giphySearchList)
        mVideoThubnailRec.setHasFixedSize(true)
        mVideoThubnailRec.setLayoutManager(GridLayoutManager(activity, 2))
        mVideoThubnailRec.setAdapter(giphyListAdapter)
    }


    override fun showProgress(show: Boolean) {
        if (show)
            mProgressBar.visibility = View.VISIBLE
        else
            mProgressBar.visibility = View.GONE
    }


    override fun showErrorMessage(error: String) {
        System.err.println("ERRORR--- $error")
    }


    override fun loadDataSuccess(list: Giphy) {
        setGiphyListAdapter(list.data)
    }


    @OnClick(R.id.btn_search)
    fun onSearchClick() {
        presenter.loadData(BuildConfig.API_KEY, mSearchEdt.text.toString(), limit.toString(), offset.toString(), "R", "en")
        showProgress(true)
    }


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        presenter.loadData(BuildConfig.API_KEY, mSearchEdt.text.toString(), limit.toString(), offset.toString(), "R", "en")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }


    class GiphyListAdapter(val router: Router, val giphyList: List<Giphy.GiphyData>) : RecyclerView.Adapter<GiphyListAdapter.ViewHolder>() {

        //this method is returning the view for each item in the list
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyListAdapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            return ViewHolder(v)
        }

        //this method is binding the data on the list
        override fun onBindViewHolder(holder: GiphyListAdapter.ViewHolder, position: Int) {
            holder.titleTxt.text = giphyList.get(position).title

            Picasso
                .get()
                .load(giphyList.get(position).images.still480w.url)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(holder.previewImg);

            holder.view.setOnClickListener(View.OnClickListener {
                router.pushController(RouterTransaction.with(DetailFragment(giphyList.get(position).id, giphyList.get(position).images.orijnalMp4.mp4)
                ).pushChangeHandler(FadeChangeHandler())
                    .popChangeHandler(FadeChangeHandler()))
            })
        }

        //this method is giving the size of the list
        override fun getItemCount(): Int {
            return giphyList.size
        }

        //the class is hodling the list view
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val previewImg = itemView.img_preview
            val titleTxt = itemView.txt_video_title
            val view = itemView
        }
    }
}
