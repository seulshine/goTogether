package app.gotogether.com.go_together;

/**
 * Created by seuls on 2017-07-07.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MyPageActivity extends Activity {

        //CalendarView 인스턴스 만들기
        CalendarView cal;
        TextView schedule;

        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_page);

            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            String username = intent.getStringExtra("username");
            String nickname = intent.getStringExtra("nickname");

            TextView viewNickname = (TextView) findViewById(R.id.nickname);
            TextView viewName = (TextView) findViewById(R.id.nickname);

            viewNickname.setText(name + "(nickname)");
            viewName.setText(name + "(nickname)");

            cal = (CalendarView) findViewById(R.id.calendar);
            schedule = (TextView) findViewById(R.id.scheduleView);

            //리스너 등록
           CalendarViewListener listener = new CalendarViewListener();
            cal.setOnDateChangeListener(listener);
        }


        //캘린더에서 현재 날짜 불러옴
        public void getDate(View view) {
            long time = cal.getDate(); //ms단위로 환산되어져 옴
            Date d1 = new Date(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d1);

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



        }

        class CalendarViewListener implements CalendarView.OnDateChangeListener {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                //일정추가하는 코드

                //일정 불러와서 보여주는 코드 - 수정예정
                schedule.setText(year + "년" + month + "월" + dayOfMonth + "일");


            }
        }



    }




