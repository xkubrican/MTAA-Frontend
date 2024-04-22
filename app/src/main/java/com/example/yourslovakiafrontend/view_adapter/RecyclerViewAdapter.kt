import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yourslovakiafrontend.databinding.PoiObjectBinding
import fiit.mtaa.yourslovakia.models.PointOfInterest

class RecyclerViewAdapter(private val data: List<PointOfInterest>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: PoiObjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(point: PointOfInterest) {
            binding.textCustomObjectName.text = point.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PoiObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size
}
