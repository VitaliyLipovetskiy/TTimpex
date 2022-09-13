package com.lvv.ttimpex2.to;


import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
public class ReportTo {
    private List<ReportDataTo> data;
    private List<ColumnTo> columnTos;

    public ReportTo(List<ReportDataTo> data, List<ColumnTo> columnTos) {
        this.data = data;
        this.columnTos = columnTos;
    }

    public List<ReportDataTo> getData() {
        return data;
    }

    public List<ColumnTo> getColumnTos() {
        return columnTos;
    }
}
