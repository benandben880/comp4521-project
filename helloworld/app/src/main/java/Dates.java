import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.helloworld.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Dates extends LinearLayout {
    Button previous,next;
    TextView CurMonth;
    GridView GV;
    Calendar calendar = Calendar.getInstance();
    private static final int Days=31;
    Context context;
    List<Date> date=new ArrayList<>();
    List<Event> event=new ArrayList<>();
    SimpleDateFormat dateFormat=new SimpleDateFormat("E yyyyy/MM/dd");
    SimpleDateFormat monthFormat=new SimpleDateFormat("MM");
    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    public Dates(Context context) {
        super(context);
    }

    public Dates(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        Initialize();
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,+1);
                SetUpCalendar();
            }
        });
    }

    public Dates(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void Initialize(){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.activity_main,this);
        next=view.findViewById(R.id.next);
        previous=view.findViewById(R.id.previous);
        GV=view.findViewById(R.id.gridview);
        CurMonth=view.findViewById(R.id.CurMonth);
    }
    private void SetUpCalendar(){
        String curMonth=dateFormat.format(calendar.getTime());
        CurMonth.setText(curMonth);
    }
}
