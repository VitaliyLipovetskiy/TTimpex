let checked= false;
// let table;
let reportAjaxUrl = "api/ts/";

const ctx = {
    ajaxUrl: reportAjaxUrl
    // updateTable: updateTableByData
    // updateTable: function () {
    //     $.get(reportAjaxUrl, updateTableByData);
    // }
}

function updateTableByData() {
    // setCookie('filterMonth', $('#filterMonth').val());
    ctx.datatableApi.destroy();
    $('#datatable').empty();
    initTableByData();
}

function initTableByData() {
    $.ajax({
        url: reportAjaxUrl,
        success: function (data) {
            // console.log(data);

            let countDays = data.data[0].daysDto.length;

            $('th#month').attr('colSpan', countDays).addClass("").text(data.tableHeader);
            // $('th#month').attr('colSpan', countDays);

            $('#datatable>thead').append('<tr id="days"></tr>');
            let days = $('#datatable>thead>tr#days');
            for (let i = 0; i < countDays; i++) {
                days.append('<th class="column-day"></th>');
            }

            columns = formColumns(data);
            // console.log(columns);
            ctx.datatableApi = $('#datatable').DataTable( {
                // dom: '<"top"f<"clear">>',
                data: data.data,
                columns: columns,
                order: [[1, "asc"]
                ],
                paging: false,
                deferRender: true,
                scrollX: true,
                scrollCollapse: true,
                fixedColumns: {
                    left: 2,
                    // right: 0
                },
                fixedHeader: {
                    header: true,
                    // footer: true
                },
                // initComplete: function () {
                //     var api = this.api();
                //     api.$('td').click(function () {
                //         // var data = this.row($(this).parents('tr')).data();
                //         console.log($(this).parent('td'));
                //         alert("tttttt");
                //         // api.search(this.innerHTML).draw();
                //     });
                // },
                // language: {
                //     fixedColumns: {
                //         button: 'Sticky Columns'
                //     }
                // },
                // buttons:[
                //     'fixedColumns'
                // ]
            } );
            // $(table.table().container()).on( 'click', 'td', function () {
            //     let cell = table.cell( this );
            //     console.log(cell.data());
            //     // $('#click-output').prepend(
            //     //     '<div>'+cell.data()+'</div>'
            //     // );
            // } );

        }
    });

}

function updateCell() {
    $('#datatable').on('dblclick', 'td', function () {
        if (ctx.datatableApi.column(this).index() < 3) return;

        $("#modalTitle").html(i18n["editTitle"]);

        let rowData = ctx.datatableApi.row(this).data();
        let cellData = ctx.datatableApi.cell(this).data();

        $('#id').val(rowData.id);
        $('#date').val(cellData.date);
        let formatter = new Intl.DateTimeFormat("ru", {
            weekday: "short",
            year: "numeric",
            month: "long",
            day: "numeric"
        });
        // console.log(cellData.penalty);
        $('#title').val(rowData.name.split('<br>')[0] + ' (' + formatter.format(new Date(cellData.date)) + ')');
        $("#worked").prop("checked", cellData.worked);
        if (cellData.worked) {
            $('#comingCorrectTime').val(cellData.comingCorrectTime).prop('disabled', false);
            $('#comingAutoTime').val(cellData.comingAutoTime);
            $('#leavingCorrectTime').val(cellData.leavingCorrectTime).prop('disabled', false);
            $('#leavingAutoTime').val(cellData.leavingAutoTime);
            $('#penalty').val(cellData.penalty).prop('disabled', false);
            $('#workedOut').val(cellData.workedOut).prop('disabled', false);
            $("#dayOff").prop("checked", cellData.dayOff);
            $('.btn-primary').prop('disabled', false);
        } else {
            $('#comingCorrectTime').prop('disabled', true);
            $('#leavingCorrectTime').prop('disabled', true);
            $('#penalty').prop('disabled', true);
            $('#workedOut').prop('disabled', true);
            $('.btn-primary').prop('disabled', true);
        }
        $('.worked-out').prop('hidden', !rowData.accountingForHoursWorked)

        // console.log(ctx.datatableApi.row(this).data());
        // id: 15, name: 'Андреев Андрей', accountingForHoursWorked: false, penalty: 31000, workedOut: 0, …

        // console.log(cellData);
        // date: '2022-10-03', comingAutoTime: null, comingCorrectTime: null, leavingAutoTime: null, leavingCorrectTime: null, …

        $('#editRow').modal();
    });
}

function save() {
    let dataForm = {};
    dataForm.id = $('#id').val();
    dataForm.date = $('#date').val();
    dataForm.comingCorrectTime = $('#comingCorrectTime').val();
    dataForm.leavingCorrectTime = $('#leavingCorrectTime').val();
    if ($('#penalty').val() != '') {
        dataForm.penalty = $('#penalty').val();
    }
    dataForm.workedOut = $('#workedOut').val();
    // console.log(dataForm);

    $.ajax({
        type: "PATCH",
        url: ctx.ajaxUrl,
        data: JSON.stringify(dataForm),
        dataType: "json",
        contentType: "application/json"
    }).done(function () {
        $("#editRow").modal("hide");
        updateTableByData();
        // getDT();
        // successNoty("common.saved");
    })
}

// $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
//     let selected = data[2];
//
//     if (checked) {
//         console.log("1 " + checked + " " + selected);
//         if (selected) {
//             return true
//         }
//         else {
//             return false
//         }
//         // return selected;
//     } else {
//         console.log("2 " + checked + " " + selected);
//         return true;
//     }
// });

// function capitalizeFirstLetter(string) {
//     return string.charAt(0).toUpperCase() + string.slice(1);
// }

function formColumns(data) {
    // console.log(data);
    let columns = [
        {
            data: "id",
            visible: false,
            render: function (data, type, row) {
                if (type === "display") {
                    return "<div class='align-middle text-center'>" + data + "</div>";
                }
                return data;
            },
            orderable: false
        },// , "title": "#$", "width": 20},
        {
            data: "name",
            render: function (data, type, row) {
                if (type === "display") {
                    //
                    //  $('td', row).eq(5).addClass('highlight');
                    //
                    let result = "<div class='align-middle cell-name'>" + data + "</div><div class='text-right cell-font-red'>";
                    if (row.penalty !== 0) {
                        result += row.penalty;
                    } else {
                        result += "<br>";
                    }
                    if (row.workedOut !== 0) {
                        result += "</div><div class='text-right'>" + row.workedOut.toFixed(3);
                    }
                    return result+ "</div>";
                }
                return data;
            },
            orderable: true
        },
        // {
        //   data: "name"
        // },
        {
            // data: "choice",
            defaultContent: false,
            render: function (data, type, row) {
                if (type === "display") {
                    // console.log(row);
                    return "<div class='align-middle text-center cell-choice'><input type='checkbox' " + (data ? "checked" : "") + " onclick='choice($(this));'/></div>";//enable($(this)," + row.id + ");
                }
                return data;
            },
            orderable: false
        }
    ];
    // return columns;
    let columnsDto = data.data[0].daysDto;
    for (let i = 0; i < columnsDto.length; i++) {
        let day = columnsDto[i];
        columns.push(
            {
                data: "daysDto." + i,
                title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                // title: '<div class="container text-primary"><div class="row"><div class="col">' + i + '<br>sa</div></div></div>',
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    // console.log(data);
                    if (type === "display") {
                        if (new Date(data.date) > new Date()) {
                            return "";
                        }
                        // Приход
                        let result = '<div id="container"><div class="text-right cell-time';
                        if (data.comingCorrectTime == null) {
                            if (data.dayOff === true || data.worked === false) {
                                result += ' box cell-dayoff';
                            } else if (data.comingAutoTime == null) {
                                result += ' box cell-red';
                            }
                            result += '">';
                            if (data.comingAutoTime != null) {
                                result += data.comingAutoTime.substring(0,5);
                            }
                        } else {
                            result += ' correct-time">' + data.comingCorrectTime.substring(0,5);
                        }
                        result += '</div><div class="text-right cell-time';
                        // Уход
                        if (data.leavingCorrectTime == null) {
                            if (data.dayOff === true || data.worked === false) {
                                result += ' box cell-dayoff';
                            } else if (data.leavingAutoTime == null) {
                                result += ' box cell-red';
                            }
                            result += '">';
                            if (data.leavingAutoTime != null) {
                                result += data.leavingAutoTime.substring(0,5);
                            }
                        } else {
                            result += ' correct-time">' + data.leavingCorrectTime.substring(0,5);
                        }
                        result += '</div></div><div id="container-penalty" class="text-center cell-font-red">';
                        // Штрафы
                        if (data.penalty !== 0) {
                            result += data.penalty;
                        }
                        // Отработанные часы
                        if (row.accountingForHoursWorked === true) {
                            result += '</div><div id="container-worked" class="text-center';
                            if (data.dayOff === true) {
                                result += '">' + (data.workedOut === 0 ? '' : data.workedOut.toFixed(3));
                            } else {
                                if (data.workedOut === 0) {
                                    result += ' cell-font-red'
                                }
                                result += '">' + data.workedOut.toFixed(3);
                            }
                        }
                        return result + '</div>';
                    }
                    return data;
                },
                createdCell: function (td, cellData, rowData, row, col) {
                    if (cellData.dayOff || !cellData.worked) {
                        $(td).css('background-color', '#898989');
                    }
                    // if (!rowData.daysOffTo[col - 3].worked) {
                    //     $(td).css('background-color', '#e0c749');
                    // }
                    if ('сб вс'.includes(columnsDto[col-3].dayOfWeek)) {
                        $(td).addClass('data-day-off');// .css('background-color', '#C0C0F3FF');
                    }
                }
            });
    }
    return columns;
}

function choice(chkbox) {
    ctx.datatableApi.cell(chkbox.closest('td')).data(chkbox.is(":checked"));

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


$(function() {

    initTableByData();
    updateCell();

});