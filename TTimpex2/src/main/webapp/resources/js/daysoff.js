let checked= false;
let ajaxUrl = "api/employees/daysoff/";
let daysOff, columnTos;

const ctx = {
    ajaxUrl: ajaxUrl,
    updateTable: function () {
        $.get(ajaxUrl, updateTableByData);
    }
}

$(function() {
    $.ajax({
        url: ajaxUrl,
        success: function(data) {
            let groupColumn = 1;
            columnTos = data[0];
            let countDays = columnTos.length;
            // console.log(data[1][0].dayOffTo);
            daysOff = data[1][0].dayOffTo;

            let days = $('#datatable>thead>tr#days');
            for (let i = 0; i < countDays; i++) {
                days.append('<th class="column-day"></th>')
            }

            columns = [
                {
                    data: "employeeTo.name",
                    render: function (data, type, row) {
                        if (type === "display") {
                            return  "<div class='align-middle cell-name'>" + data + "</div>";
                        }
                        return data;
                    },
                    orderable: false
                },
                {
                    data: 'employeeTo.department',
                    render: function (data, type, row) {
                        if (type === "display") {
                            if (data === null) {
                                return '';
                            } else {
                                return data;
                            }
                        }
                        return data;
                    },
                    orderable: false
                },
                {
                    // data: "choice",
                    defaultContent: false,
                    render: function (data, type, row) {
                        if (type === "display") {
                            return "<div class='align-middle text-center cell-choice'><input class='chk-select' type='checkbox' " + (data ? "checked" : "") + " onclick='choice($(this)," + row.employeeTo.id + ");'/></div>";//enable($(this)," + row.id + ");
                        }
                        return data;
                    },
                    orderable: false
                }
            ];

            for (let  i = 0; i < countDays; i++) {
                let day = columnTos[i];
                columns.push(
                    {
                        data: "dayOffTo.dayOf",
                        title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                        orderable: false,
                        defaultContent: false,
                        className: function () {
                            return 'day-off';
                        },
                        render: function (data, type, row, meta) {
                            if (type === "display") {
                                return "<div class='align-middle text-center cell-choice'><input type='checkbox' " + (data ? "checked" : "") + " onclick='choiceDayOff($(this)," + row.employeeTo.id + ", " + (meta.col - 3) +");'/></div>";
                            }
                            return data;
                        },
                        createdCell: function (td, cellData, rowData, row, col) {
                            let text = 'сб вс';
                            if (text.includes(columnTos[col-3].dayOfWeek)) {
                                $(td).css('background-color', '#C0C0F3FF');
                            }
                        }
                    }
                );
            }

            ctx.datatableApi = $('#datatable').DataTable({
                data: data[1],
                columns: columns,
                order: [[groupColumn,'asc']],
                paging: false,
                deferRender: true,
                scrollX: true,
                scrollCollapse: true,
                fixedColumns: {
                    left: 2,
                //     righ
                },
                // buttons: [
                //     'copy', 'excel', 'pdf'
                // ],
                fixedHeader: {
                    header: true,
                    // footer: true
                },
                columnDefs: [
                    {
                        visible: false,
                        targets: groupColumn
                    }
                ],
                drawCallback: function (settings) {
                    let api = this.api();
                    let rows = api.rows({ page: 'current' }).nodes();
                    let last = null;

                    api
                        .column(groupColumn, { page: 'current' })
                        .data()
                        .each(function (group, i) {
                            // if (group === null) {
                            //     if (last !== group) {
                            //         $(rows)
                            //             .eq(i)
                            //             .before('<tr class="group"><td colspan="2">' + group.name + '</td></tr>');
                            //
                            //         last = group;
                            //     }
                            // } else {
                                if (last !== group) {
                                    $(rows)
                                        .eq(i)
                                        .before('<tr class="group"><td colspan="2">' + group + '</td></tr>');

                                    last = group;
                                }
                            // }
                        });
                },
            });
        }
    });
});

function choiceDayOff(chkbox, employeeId, day) {
    let checked = chkbox.is(":checked");
    ctx.datatableApi.cell((employeeId - 1), day + 3).data(checked);
    // table.cell((id - 1), 1).data(chkbox.is(":checked"));

//  https://stackoverflow.com/a/22213543/548473
//     $.ajax({
//         url: userAjaxUrl + id,
//         type: "POST",
//         data: "enabled=" + enabled
//     }).done(function () {
//         chkbox.closest("tr").attr("data-user-enabled", enabled);
//         successNoty(enabled ? "common.enabled" : "common.disabled");
//     }).fail(function () {
//         $(chkbox).prop("checked", !enabled);
//     });
}

function choice(chkbox, id) {
    ctx.datatableApi.cell((id - 1), 2).data(chkbox.is(":checked"));
}