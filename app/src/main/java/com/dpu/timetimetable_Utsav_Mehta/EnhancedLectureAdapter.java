package com.dpu.timetimetable_Utsav_Mehta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.List;

public class EnhancedLectureAdapter extends RecyclerView.Adapter<EnhancedLectureAdapter.LectureViewHolder> {
    private List<Lecture> lectures;
    private Context context;
    private OnLectureClickListener listener;
    private boolean isTeacherView = false;

    public interface OnLectureClickListener {
        void onLectureClick(Lecture lecture);
        void onLectureEdit(Lecture lecture);
        void onLectureDelete(Lecture lecture);
    }

    public EnhancedLectureAdapter(Context context, List<Lecture> lectures) {
        this.context = context;
        this.lectures = lectures != null ? lectures : new ArrayList<>();
    }

    public EnhancedLectureAdapter(Context context, List<Lecture> lectures, boolean isTeacherView) {
        this.context = context;
        this.lectures = lectures != null ? lectures : new ArrayList<>();
        this.isTeacherView = isTeacherView;
    }

    public void setOnLectureClickListener(OnLectureClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LectureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lecture_enhanced, parent, false);
        return new LectureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureViewHolder holder, int position) {
        Lecture lecture = lectures.get(position);
        holder.bind(lecture);
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public void updateLectures(List<Lecture> newLectures) {
        this.lectures = newLectures != null ? newLectures : new ArrayList<>();
        notifyDataSetChanged();
    }

    class LectureViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private TextView lectureName;
        private TextView classroom;
        private TextView teacher;
        private TextView timeRange;
        private Chip dayChip;
        private View editButton;
        private View deleteButton;

        public LectureViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.lectureCard);
            lectureName = itemView.findViewById(R.id.lectureName);
            classroom = itemView.findViewById(R.id.classroom);
            teacher = itemView.findViewById(R.id.teacher);
            timeRange = itemView.findViewById(R.id.timeRange);
            dayChip = itemView.findViewById(R.id.dayChip);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            // Set click listeners
            cardView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onLectureClick(lectures.get(getAdapterPosition()));
                }
            });

            editButton.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onLectureEdit(lectures.get(getAdapterPosition()));
                }
            });

            deleteButton.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onLectureDelete(lectures.get(getAdapterPosition()));
                }
            });

            // Show/hide edit/delete buttons based on view type
            if (isTeacherView) {
                editButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                editButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
            }
        }

        public void bind(Lecture lecture) {
            lectureName.setText(lecture.getName());
            classroom.setText(lecture.getClassroom());
            teacher.setText(lecture.getTeacher());
            timeRange.setText(lecture.getTimeRange());
            dayChip.setText(lecture.getDayOfWeek());

            // Set different colors for different days
            int colorRes = getDayColor(lecture.getDayOfWeek());
            dayChip.setChipBackgroundColorResource(colorRes);
        }

        private int getDayColor(String day) {
            switch (day.toLowerCase()) {
                case "monday": return R.color.monday_color;
                case "tuesday": return R.color.tuesday_color;
                case "wednesday": return R.color.wednesday_color;
                case "thursday": return R.color.thursday_color;
                case "friday": return R.color.friday_color;
                case "saturday": return R.color.saturday_color;
                case "sunday": return R.color.sunday_color;
                default: return R.color.default_day_color;
            }
        }
    }
}
