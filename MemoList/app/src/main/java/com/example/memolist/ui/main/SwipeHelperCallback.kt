package com.example.memolist.ui.main

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.R
import kotlin.math.min

class SwipeHelperCallback(private val adapter: ListAdapter): ItemTouchHelper.Callback() {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f
        previousPosition = viewHolder.adapterPosition
        getDefaultUIUtil().clearView(getView(viewHolder))
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)
            if(x == -clamp){
                getView(viewHolder).animate().translationX(-clamp).setDuration(100L).start()
                return
            }
            currentDx = x
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
        }
    }
    fun clampViewPositionHorizontal(view: View, dX:Float, isClamped: Boolean, isCurrentlyActive: Boolean): Float{
        val max = 0f
        val newX = if(isClamped){
            if(isCurrentlyActive)
                if(dX < 0) dX/3 - clamp
                else dX - clamp
            else -clamp
        }else dX/2
        return min(newX, max)
    }
    private fun getView(viewHolder: RecyclerView.ViewHolder): View = viewHolder.itemView.findViewById(R.id.listViewGroup)

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        setTag(viewHolder, currentDx <= -clamp)
        return 2f
    }
    private fun setTag(viewHolder:RecyclerView.ViewHolder, isClamped:Boolean){
        viewHolder.itemView.tag = isClamped
    }
    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean{
        return viewHolder.itemView.tag as? Boolean?: false
    }
    fun setClamp(clamp:Float){
        this.clamp = clamp
    }
    fun removePreviousClamp(recyclerView: RecyclerView){
        if(currentPosition == previousPosition)
            return
        previousPosition?.let{
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it)?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }
}