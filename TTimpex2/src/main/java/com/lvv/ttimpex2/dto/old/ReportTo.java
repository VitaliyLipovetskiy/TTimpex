package com.lvv.ttimpex2.dto.old;


import com.lvv.ttimpex2.dto.ReportDataTo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReportTo {
    private List<ReportDataTo> data;
    private String tableHeader;
}
