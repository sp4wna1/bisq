package bisq.android.chart

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight

class Marker (context: Context) : MarkerView(context, R.layout.marker) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        val valueView = findViewById<TextView>(R.id.ValueView)

        valueView.text = (e?.y?.toString())





//        R.id.dateView
//        (e?.x?.toLong())
//



    }
}
