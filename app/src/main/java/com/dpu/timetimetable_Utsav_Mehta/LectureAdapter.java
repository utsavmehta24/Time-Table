package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class LectureAdapter extends ArrayAdapter<Lecture> {
    public LectureAdapter(Context context, ArrayList<Lecture> lectures) {
        super(context, 0, lectures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Lecture currentLecture = getItem(position);

        TextView lectureNameTextView = listItemView.findViewById(R.id.lectureNameTextView);
        TextView classroomTextView = listItemView.findViewById(R.id.classroomTextView);
        TextView teacherTextView = listItemView.findViewById(R.id.teacherTextView);


        lectureNameTextView.setText(currentLecture.getName());
        classroomTextView.setText(currentLecture.getClassroom());
        teacherTextView.setText(currentLecture.getTeacher());



        return listItemView;
    }
}
