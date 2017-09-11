package creepersan.lightmanagesystemandroid.Fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.view.View
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseFragment

class AreaFragment:BaseFragment(){
    @BindView(R.id.fragmentAreaBedroom) lateinit var bedRoomCard:CardView
    @BindView(R.id.fragmentAreaLivingRoom) lateinit var livingRoomCard:CardView
    @BindView(R.id.fragmentAreaStair) lateinit var stairCard:CardView
    @BindView(R.id.fragmentAreaKitchen) lateinit var kitchenCard:CardView

    override fun getLayoutID(): Int = R.layout.fragment_area

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bedRoomCard.setOnClickListener{
            showModeDialog(bedRoomCard)
        }
        livingRoomCard.setOnClickListener {
            showModeDialog(livingRoomCard)
        }
        stairCard.setOnClickListener {
            show2ModeDialog(stairCard)
        }
        kitchenCard.setOnClickListener {
            showModeDialog(kitchenCard)
        }
    }

    private fun showModeDialog(cardView: CardView){
        val builder = AlertDialog.Builder(cardView.context)
        builder.setTitle("设定模式")
        builder.setItems(arrayOf("控制模式","自动模式","派对模式"),{ dialogInterface, i ->
            when(cardView){
                bedRoomCard ->{

                }
                livingRoomCard -> {

                }
                kitchenCard -> {

                }
            }
        })
        builder.show()
    }

    private fun show2ModeDialog(cardView: CardView){
        val builder = AlertDialog.Builder(cardView.context)
        builder.setTitle("设定模式")
        builder.setItems(arrayOf("控制模式","派对模式"),{ dialogInterface, i ->

        })
        builder.show()
    }

}
