package com.github.khovap.coursework.bookingsource_main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum DayOfWeekRu {
        SUNDAY ("Воскресенье",7),
        MONDAY ("Понедельник",1),
        TUESDAY ("Вторник",2),
        WEDNESDAY ("Среда",3),
        THURSDAY ("Четверг",4),
        FRIDAY ("Пятница",5),
        SATURDAY ("Суббота",6);

        private String title;
        private int value;

        DayOfWeekRu(String title, int value) {
                this.title = title;
                this.value = value;
        }


        public String getTitle() {
                return title;
        }

        public int getValue() {
                return value;
        }

        @Override
        public String toString() {
                return title;
        }
}
