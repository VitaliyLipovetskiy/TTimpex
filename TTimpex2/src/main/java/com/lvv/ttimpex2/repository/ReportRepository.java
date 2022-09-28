package com.lvv.ttimpex2.repository;

import com.lvv.ttimpex2.to.ColumnTo;
import com.lvv.ttimpex2.to.DayTo;
import com.lvv.ttimpex2.to.ReportDataTo;
import com.lvv.ttimpex2.to.ReportTo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
public class ReportRepository {

//    public static Integer count = 0;

    public ReportTo getReportTo() {
        List<ReportDataTo> reportDataToes = new ArrayList<>();

        List<DayTo> daysTo = new ArrayList<>();
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 1), "9:00", "", "", "18:56"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 2), "", "", "", "", 3000));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 3), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 4), "9:26", "", "19:00", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 5), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 6), "8:50", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 7), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 8), "9:15", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 9), "8:55", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 10), "8:58", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 11), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 12), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 13), "9:08", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 14), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 15), "8:54", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 16), "9:20", "", "19:00", "", 540));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 17), "8:49", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 18), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 19), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 20), "9:08", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 21), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 22), "8:50", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 23), "8:56", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 24), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 25), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 26), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 27), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 28), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 29), "9:01", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 30), "8:57", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 31), "9:20", "", "19:00", "", 540));
        ReportDataTo reportDataTo = new ReportDataTo(1, "Іванов", false, 4080, 0.0, daysTo);
        reportDataToes.add(reportDataTo);

        daysTo = new ArrayList<>();
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 1), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 2), "12:45", "", "19:00", "", true, 0.625));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 3), "9:01", "", "19:00", "", 1.0));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 4), "", "", "19:00", "", 0.0));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 5), "9:25", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 6), "8:58", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 7), "9:13", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 8), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 9), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 10), "8:55", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 11), "8:45", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 12), "9:03", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 13), "8:45", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 14), "9:12", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 15), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 16), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 17), "8:55", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 18), "8:54", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 19), "9:20", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 20), "9:01", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 21), "9:13", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 22), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 23), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 24), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 25), "8:50", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 26), "8:56", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 27), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 28), "9:16", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 29), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 30), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 31), "9:02", "", "19:00", ""));
        reportDataTo = new ReportDataTo(2, "Сидоров", true, 0, 1.625, daysTo);
        reportDataToes.add(reportDataTo);

        daysTo = new ArrayList<>();
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 1), "9:00", "", "", "18:56"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 2), "", "", "", "", 3000));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 3), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 4), "9:26", "", "19:00", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 5), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 6), "8:50", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 7), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 8), "9:15", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 9), "8:55", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 10), "8:58", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 11), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 12), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 13), "9:08", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 14), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 15), "8:54", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 16), "9:20", "", "19:00", "", 540));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 17), "8:49", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 18), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 19), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 20), "9:08", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 21), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 22), "8:50", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 23), "8:56", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 24), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 25), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 26), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 27), "9:10", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 28), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 29), "9:01", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 30), "8:57", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 31), "9:20", "", "19:00", "", 540));
        reportDataTo = new ReportDataTo(3, "Давыдов",false, 4080, 0.0, daysTo);
        reportDataToes.add(reportDataTo);

        daysTo = new ArrayList<>();
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 1), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 2), "12:45", "", "19:00", "", true, 0.625));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 3), "9:01", "", "19:00", "", 1.0));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 4), "", "", "19:00", "", 0.0));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 5), "9:25", "", "", "19:00", 450));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 6), "8:58", "", "", "18:12", 540));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 7), "9:13", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 8), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 9), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 10), "8:55", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 11), "8:45", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 12), "9:03", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 13), "8:45", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 14), "9:12", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 15), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 16), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 17), "8:55", "", "", "19:00"));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 18), "8:54", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 19), "9:20", "", "19:00", "", 400));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 20), "9:01", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 21), "9:13", "", "", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 22), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 23), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 24), "9:12", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 25), "8:50", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 26), "8:56", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 27), "9:01", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 28), "9:16", "", "19:00", ""));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 29), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 30), "", "", "", "", true));
//        daysTo.add(new DayTo(LocalDate.of(2022, 7, 31), "9:02", "", "19:00", ""));
        reportDataTo = new ReportDataTo(4, "Петров", true, 1390, 1.625, daysTo);
        reportDataToes.add(reportDataTo);

        LocalDate startDate = LocalDate.of(2022, 7, 1);
        LocalDate endDate = LocalDate.of(2022, 7, 31).plusDays(1);
        List<ColumnTo> columnTos = new ArrayList<>();
        startDate.datesUntil(endDate).forEach(date -> columnTos.add(new ColumnTo(date)));

//        reportTos.add(new ReportTo(3, "Петров", new ArrayList<>()));

        return new ReportTo(reportDataToes, columnTos);
    }


}
