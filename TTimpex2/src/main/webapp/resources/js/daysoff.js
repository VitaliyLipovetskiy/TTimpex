let checked= false;
let ajaxUrl = "api/employees/daysoff/";
let daysOff, columnTos;

const ctx = {
    ajaxUrl: ajaxUrl,
    // updateTable: function () {
    //     $.get(ajaxUrl, updateTableByData);
    // }
}

function getCookie(cookieName) {
    let results = document.cookie.match('(^|;) ?' + cookieName + '=([^;]*)(;|$)');

    if (results)
        return (unescape(results[2]));
    else
        return null;
}

function setCookie(name, value, expY, expM, expD, path, domain, secure) {
    let cookieString = name + "=" + escape (value);
    if (expY) {
        let expires = new Date(expY, expM, expD);
        cookieString += "; expires=" + expires.toGMTString();
    }

    if (path)
        cookieString += "; path=" + escape (path);

    if (domain)
        cookieString += "; domain=" + escape (domain);

    if (secure)
        cookieString += "; secure";

    document.cookie = cookieString;
}

$(function() {
    let filterMonth = getCookie('filterMonth');
    if (filterMonth === null)
        filterMonth = new Date().toLocaleDateString().substring(3).split('.').reverse().join('-');
    $('#filterMonth').val(filterMonth);
    initTableByData();
});

function choiceDayOff(chkbox, employeeId, day) {
    let checked = chkbox.is(":checked");
    console.log(day);
    // console.log(chkbox.is(":checked"));
    // $(chkbox).prop("checked", dayOff);
    // ctx.datatableApi.cell(chkbox.closest('td')).data(chkbox.is(":checked"));
    // console.log(chkbox.closest('td'));
    // console.log(ctx.datatableApi.cell(chkbox.closest('td')).data());
    // // table.cell((id - 1), 1).data(chkbox.is(":checked"));
    let dayOff = {
        'date': $('#filterMonth').val() + (('' + day).length === 1 ? '-0' : '-') + day,
        'off': checked
    };
    // console.log(dayOff);

//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajaxUrl + employeeId,
        type: "POST",
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
            columnTos = data[0];
            let countDays = columnTos.length;
            // console.log(data[1]);
            daysOff = data[1][0].dayOffTo;

            let days = $('#datatable>thead>tr#days');
            for (let i = 0; i < countDays; i++) {
                days.append('<th class="column-day"></th>')
            }

            columns = [
                {
                    data: 'employeeTo.name',
                    title: '<div class="text-center"><spring:message code="employee.name"/></div>',
                    render: function (data, type, row) {
                        if (type === "display") {
                            return  "<div class='align-middle cell-name'>" + data + "</div>";
                        }
                        return data;
                    },
                    orderable: false
                },
                {
                    data: 'employeeTo.departmentName',
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
                    visible: false,
                    orderable: false
                },
                {
                    // data: "choice",
                    title: '<div class="align-middle text-center cell-choice"><input type="checkbox" id="filtered" onClick="selectFilter($(this));"/></div>',
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
                        data: "dayOffTo.dayOf",
                        title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                        orderable: false,
                        defaultContent: false,
                        className: function () {
                            return 'day-off';
                        },
                        render: function (data, type, row, meta) {
                            if (type === "display") {
                                // console.log(row.employeeTo.id);
                                // console.log(meta.col - 2);
                                // console.log(row);
                                // console.log(row.daysOff[meta.col - 3].dayOff);
                                let dayOff = row.daysOff[meta.col - 3].dayOff;
                                let worked = row.daysOff[meta.col - 3].worked;
                                return "<div class='align-middle text-center cell-choice'>" +
                                    "<input type='checkbox' " + (!worked || dayOff ? "checked" : "") + (!worked ? " disabled" : "") +
                                    " onclick='choiceDayOff($(this)," + row.employeeTo.id + ", " + (meta.col - 2) +");'/></div>";
                            }
                            return data;
                        },
                        createdCell: function (td, cellData, rowData, row, col) {
                            // console.log(td);
                            // console.log(cellData);
                            // console.log(rowData);
                            // console.log(row);
                            // console.log(col);
                            if (!rowData.daysOff[col - 3].worked) {
                                $(td).css('background-color', '#e0c749');
                            }
                            if ('сб вс'.includes(columnTos[col-3].dayOfWeek)) {
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