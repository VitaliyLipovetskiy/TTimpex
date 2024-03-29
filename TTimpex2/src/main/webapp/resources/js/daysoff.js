let checked= false;
let ajaxUrl = "api/days_off/employees/";
let daysOff, columnTos;

const ctx = {
    ajaxUrl: ajaxUrl,
    // updateTable: function () {
    //     $.get(ajaxUrl, updateTableByData);
    // }
}

$(function() {
    let filterMonth = getCookie('filterMonth');
    if (filterMonth == null)
        filterMonth = new Date().toLocaleDateString().substring(3).split('.').reverse().join('-');
    $('#filterMonth').val(filterMonth);
    initTableByData();
});

function choiceDayOff(chkbox, employeeId, day) {
    let checked = chkbox.is(":checked");
    // console.log(day);
    // console.log(checked);
    // console.log(employeeId);
    // $(chkbox).prop("checked", dayOff);
    // ctx.datatableApi.cell(chkbox.closest('td')).data(chkbox.is(":checked"));
    // console.log(chkbox.closest('td'));
    // console.log(ctx.datatableApi.cell(chkbox.closest('td')).data());
    // // table.cell((id - 1), 1).data(chkbox.is(":checked"));
    let dayOff = {
        'date': $('#filterMonth').val() + (('' + day).length === 1 ? '-0' : '-') + day,
        'dayOff': checked
    };
    // console.log(dayOff);
    // console.log(ajaxUrl + employeeId);
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajaxUrl + employeeId,
        type: "PATCH",
        data: JSON.stringify(dayOff),
        dataType: "json",
        contentType: "application/json"
    }).done(function () {
//         chkbox.closest("tr").attr("data-user-enabled", enabled);
//         successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !checked);
    });
}

function choice(chkbox) {
    ctx.datatableApi.cell(chkbox.closest('td')).data(chkbox.is(":checked"));
}

function updateTableByData() {
    setCookie('filterMonth', $('#filterMonth').val());
    ctx.datatableApi.destroy();
    $('#datatable').empty();
    initTableByData();
}

function initTableByData() {
    $.ajax({
        url: ajaxUrl,
        data: $('#filter').serialize(),
        success: function(data) {
            let groupColumn = 1;
            columnTos = data[0].daysOffDto;
            let countDays = columnTos.length;
            // console.log(data[0]);
            // daysOff = data[1][0].dayOffTo;

            let days = $('#datatable>thead>tr#days');
            for (let i = 0; i < countDays; i++) {
                days.append('<th class="column-day"></th>')
            }

            columns = [
                {
                    data: 'employeeDto.fullName',
                    // title: '<div class="text-center"><spring:message code="employee.name"/></div>',
                    // title: '<spring:message code="employee.name"/>',
                    render: function (data, type, row) {
                        if (type === "display") {
                            return  "<div class='align-middle cell-name'>" + data + "</div>";
                        }
                        return data;
                    },
                    orderable: false
                },
                {
                    data: 'employeeDto.departmentName',
                    render: function (data, type, row) {
                        if (type === "display") {
                            if (data == null) {
                                return '';
                            } else {
                                return data;
                            }
                        }
                        return data;
                    },
                    visible: false,
                    orderable: false
                },
                {
                    // data: "choice",
                    // title: '<div class="align-middle text-center cell-choice"><input type="checkbox" id="filtered" onClick="selectFilter($(this));"/></div>',
                    defaultContent: false,
                    render: function (data, type, row) {

                        if (type === "display") {
                            return "<div class='align-middle text-center cell-choice'><input class='chk-select' type='checkbox' " + (data ? "checked" : "") + " onclick='choice($(this));'/></div>";//enable($(this)," + row.id + ");
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
                        data: "dayOffAndWorkedDto.dayOf",
                        title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                        // title: '<div class="text-center' + ("сб вс".includes(day.dayOfWeek) ? " data-day-off" : "") +'">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                        orderable: false,
                        defaultContent: false,
                        className: function () {
                            return 'day-off';
                        },
                        render: function (data, type, row, meta) {
                            if (type === "display") {
                                let dayOff = row.daysOffDto[meta.col - 3].dayOff;
                                let worked = row.daysOffDto[meta.col - 3].worked;
                                // console.log(row);
                                return "<div class='align-middle text-center cell-choice'>" +
                                    "<input type='checkbox' " + (!worked || dayOff ? "checked" : "") + (!worked ? " disabled" : "") +
                                    " onclick='choiceDayOff($(this), \"" + row.employeeDto.id + "\", " + (meta.col - 2) +");'/></div>";
                            }
                            return data;
                        },
                        createdCell: function (td, cellData, rowData, row, col) {
                            if (!rowData.daysOffDto[col - 3].worked) {
                                $(td).css('background-color', '#898989');
                            }
                            if ('сб вс'.includes(columnTos[col-3].dayOfWeek)) {
                                $(td).addClass('data-day-off');// .css('background-color', '#C0C0F3FF');
                            }

                        }
                    }
                );
            }

            ctx.datatableApi = $('#datatable').DataTable({
                data: data,
                columns: columns,
                order: [[groupColumn,'asc']],
                paging: false,
                // info: true,
                // language: {
                //   search: i18n["common.search"]
                // },
                // ajax: {
                //     url: ctx.ajaxUrl,
                //     dataSrc: ""
                // },
                deferRender: true,
                scrollX: true,
                scrollY: '50vh',
                scrollCollapse: true,
                fixedColumns: {
                    left: 2,
                    //     righ
                },
                // buttons: [
                //     'copy', 'excel', 'pdf'
                // ],
                // fixedHeader: {
                //     header: true,
                // footer: true
                // },
                // columnDefs: [
                //     {
                //         visible: false,
                //         targets: groupColumn
                //     }
                // ],
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
                            if (last !== group && group !== '') {
                                $(rows)
                                    .eq(i)
                                    .before('<tr class="group"><td colspan="2">' + group + '</td></tr>');

                                last = group;
                            }
                            // }
                        });
                },
            });


            // var table = $('#datatable').DataTable();
            // $('#datatable').on('click', 'input[type="checkbox"]', function () {
            //
            //     console.log("checkBox");
            //
            //     // Get the TR for the clicked checkbox
            //     var cell = $(this).closest('td');
            //
            //     // Get the row data for the TD
            //     var data = table.row( cell ).data();
            //     console.log('Using TD:', data)
            //
            //     // Get the Parent for the clicked checkbox
            //     var cell = $(this).parent();
            //
            //     // Get the row data for the Parent
            //     var data = table.row( cell ).data();
            //     console.log('Using Parent:', data)
            //
            //     // Get the TR for the clicked checkbox
            //     var row = $(this).closest('tr');
            //
            //     // Get the row data for the TR
            //     var data = table.row( row ).data();
            //     console.log('Using TR:', data)
            //
            //     // Need to use array notation
            //     console.log('Checked name from TR:', data[0]);
            //
            //
            // });

        }
    });
}