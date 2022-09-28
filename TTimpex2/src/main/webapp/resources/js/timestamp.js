let checked= false;
// let table;
let reportAjaxUrl = "api/ts/";

const ctx = {
    ajaxUrl: reportAjaxUrl,
    // updateTable: updateTableByData
    updateTable: function () {
        $.get(reportAjaxUrl, updateTableByData);
    }
}

function getDT() {
    $.ajax({
        url: reportAjaxUrl,
        success: function (data) {
            // console.log(data);

            let countDays = data.columnTos.length;

            $('th#month').attr('colSpan', countDays).text('Август (25)');
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
        console.log(ctx.datatableApi.row(this).data());
        // console.log(ctx.datatableApi.column(this).data());
        console.log(ctx.datatableApi.cell(this).data());

        // form.find(":input").val("");
        $("#modalTitle").html(i18n["editTitle"]);


        $('#editRow').modal();
    });
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
                        result += "</div><div class='text-right'>" + row.workedOut;
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
    let columnTos = data.columnTos;
    for (let i = 0; i < columnTos.length; i++) {
        let day = columnTos[i];
        columns.push(
            {
                data: "daysTo." + i,
                title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                // title: '<div class="container text-primary"><div class="row"><div class="col">' + i + '<br>sa</div></div></div>',
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    // console.log(data);
                    if (type === "display") {
                        // Приход
                        let result = '<div id="container"><div class="text-right cell-time';
                        if (data.comingCorrectTime == null) {
                            if (data.dayOff === true) {
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
                            if (data.dayOff === true) {
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
                                result += '">' + (data.workedOut === 0 ? '' : data.workedOut);
                            } else {
                                if (data.workedOut === 0) {
                                    result += ' cell-font-red'
                                }
                                result += '">' + data.workedOut;
                            }
                        }
                        return result + '</div>';
                    }
                    return data;
                },
                createdCell: function (td, cellData, rowData, row, col) {
                    if (cellData.dayOff) {
                        $(td).css('background-color', '#898989');
                    }
                    // if (!rowData.daysOffTo[col - 3].worked) {
                    //     $(td).css('background-color', '#e0c749');
                    // }
                    if ('сб вс'.includes(columnTos[col-3].dayOfWeek)) {
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

    getDT();
    updateCell();

});