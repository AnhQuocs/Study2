package com.example.test.drag_drop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun DragDropList(
    items: List<String>,
    onMove: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState
) {
    val scope = rememberCoroutineScope()
    val dragDropListState = rememberDragDropListState(
        onMove = onMove,
        lazyListState = lazyListState
    )

    LazyColumn(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { offset -> dragDropListState.onDragStart(offset) },
                    onDrag = { change, offset ->
                        change.consumeAllChanges()
                        dragDropListState.onDrag(offset)

                        val scrollAmount = dragDropListState.checkForOverScroll()
                        if (scrollAmount != 0f) {
                            dragDropListState.overscrollJob?.cancel()
                            dragDropListState.overscrollJob = scope.launch {
                                dragDropListState.lazyListState.scrollBy(scrollAmount)
                            }
                        }
                    },
                    onDragEnd = { dragDropListState.onDragInterrupted() },
                    onDragCancel = { dragDropListState.onDragInterrupted() }
                )
            }
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        state = dragDropListState.lazyListState
    ) {
        itemsIndexed(items, key = { _, item -> item }) { index, item ->
            val isDragging = index == dragDropListState.currentIndexOfDraggedItem
            val translationY = if (isDragging) dragDropListState.elementDisplacement ?: 0f else 0f
            val bgColor = if (isDragging) Color(0xFFF0F0F0) else Color.White

            Column(
                modifier = Modifier
                    .graphicsLayer { this.translationY = translationY }
                    .background(bgColor, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = item,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}